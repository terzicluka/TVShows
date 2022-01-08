import psycopg2
from flask import Flask, render_template, send_file, request
import csv
from CSVJSONConverter import convert_csv_to_json
import json
from six.moves.urllib.request import urlopen
from functools import wraps
from flask import session
from flask import Flask, request, jsonify, _request_ctx_stack
from flask_cors import cross_origin
from jose import jwt

AUTH0_DOMAIN = 'dev-5fsypyq5.us.auth0.com'
API_AUDIENCE = 'http://localhost:4000/'
ALGORITHMS = ["RS256"]

app = Flask(__name__, template_folder='template')

connection = psycopg2.connect(
    host="localhost",
    database="TVShows",
    user="postgres",
    password="kikoniko1",
    port="5433"
)

cursor = connection.cursor()

# Error handler


class AuthError(Exception):
    def __init__(self, error, status_code):
        self.error = error
        self.status_code = status_code


@app.errorhandler(AuthError)
def handle_auth_error(ex):
    response = jsonify(ex.error)
    response.status_code = ex.status_code
    return response


def get_token_auth_header():
    """Obtains the Access Token from the Authorization Header
    """
    auth = request.headers.get("Authorization", None)
    if not auth:
        raise AuthError({"code": "authorization_header_missing",
                        "description":
                            "Authorization header is expected"}, 401)

    parts = auth.split()

    if parts[0].lower() != "bearer":
        raise AuthError({"code": "invalid_header",
                        "description":
                            "Authorization header must start with"
                            " Bearer"}, 401)
    elif len(parts) == 1:
        raise AuthError({"code": "invalid_header",
                        "description": "Token not found"}, 401)
    elif len(parts) > 2:
        raise AuthError({"code": "invalid_header",
                        "description":
                            "Authorization header must be"
                            " Bearer token"}, 401)

    token = parts[1]
    return token


def requires_auth(f):
    """Determines if the Access Token is valid
    """
    @wraps(f)
    def decorated(*args, **kwargs):
        token = get_token_auth_header()
        jsonurl = urlopen("https://"+AUTH0_DOMAIN+"/.well-known/jwks.json")
        jwks = json.loads(jsonurl.read())
        unverified_header = jwt.get_unverified_header(token)
        rsa_key = {}
        for key in jwks["keys"]:
            if key["kid"] == unverified_header["kid"]:
                rsa_key = {
                    "kty": key["kty"],
                    "kid": key["kid"],
                    "use": key["use"],
                    "n": key["n"],
                    "e": key["e"]
                }
        if rsa_key:
            try:
                payload = jwt.decode(
                    token,
                    rsa_key,
                    algorithms=ALGORITHMS,
                    audience=API_AUDIENCE,
                    issuer="https://"+AUTH0_DOMAIN+"/"
                )
            except jwt.ExpiredSignatureError:
                raise AuthError({"code": "token_expired",
                                "description": "token is expired"}, 401)
            except jwt.JWTClaimsError:
                raise AuthError({"code": "invalid_claims",
                                "description":
                                    "incorrect claims,"
                                    "please check the audience and issuer"}, 401)
            except Exception:
                raise AuthError({"code": "invalid_header",
                                "description":
                                    "Unable to parse authentication"
                                    " token."}, 401)

            _request_ctx_stack.top.current_user = payload
            return f(*args, **kwargs)
        raise AuthError({"code": "invalid_header",
                        "description": "Unable to find appropriate key"}, 401)
    return decorated


@app.route('/')
def shows():
    cursor.execute(
        "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country")
    rows = cursor.fetchall()
    fp = open(
        '/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv', 'w')
    my_file = csv.writer(fp)
    csv_header = ['countryid', 'genreid', 'actorid', 'showid', 'showname', 'description',
                  'numberofreviews', 'averagerating', 'isdiscontinued', 'actorname', 'genrename', 'countryname']
    my_file.writerow(csv_header)
    my_file.writerows(rows)
    fp.close()
    convert_csv_to_json()
    data_for_html = transform_data_for_html(rows)
    return render_template("datatable.html", rows=data_for_html)


def requires_scope(required_scope):
    """Determines if the required scope is present in the Access Token
    Args:
        required_scope (str): The scope required to access the resource
    """
    token = get_token_auth_header()
    unverified_claims = jwt.get_unverified_claims(token)
    if unverified_claims.get("scope"):
        token_scopes = unverified_claims["scope"].split()
        for token_scope in token_scopes:
            if token_scope == required_scope:
                return True
    return False


@app.route('/search', methods=['POST'])
def handle_search():
    search_option = request.form.get("searchOption")
    search = request.form.get("search")
    if search_option == "wildcard":
        if search == "":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country")
        else:
            try:
                search = int(search)
                cursor.execute(
                    "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where averagerating = %s",
                    [search]
                )
            except:
                search = request.form.get("search")
                cursor.execute(
                    "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where genrename = %s or countryname = %s or showname = %s or isdiscontinued = %s",
                    (search, search, search, search)
                )
    else:
        if search_option == "showname":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where showname = %s",
                [search]
            )
        if search_option == "genrename":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where genrename = %s",
                [search]
            )
        if search_option == "countryname":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where countryname = %s",
                [search]
            )
        if search_option == "genrename":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where genrename = %s",
                [search]
            )
        if search_option == "isdiscontinued":
            cursor.execute(
                "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where isdiscontinued = %s",
                [search]
            )
        if search_option == "averagerating":
            try:
                search = int(search)
                cursor.execute(
                    "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country where averagerating = %s",
                    [search]
                )
            except:
                return "Wrong input"
    rows = cursor.fetchall()
    fp = open(
        '/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv', 'w')
    my_file = csv.writer(fp)
    csv_header = ['countryid', 'genreid', 'actorid', 'showid', 'showname', 'description',
                  'numberofreviews', 'averagerating', 'isdiscontinued', 'actorname', 'genrename', 'countryname']
    my_file.writerow(csv_header)
    my_file.writerows(rows)
    fp.close()
    convert_csv_to_json()
    data_for_html = transform_data_for_html(rows)
    if len(rows) == 0:
        return "No results found"
    return render_template("datatable.html", rows=data_for_html)


def transform_data_for_html(data):
    current_index = 0
    table_data = []
    for row in data:
        print(row)
        if current_index % 2 == 0:
            table_data.append(
                [row[4], row[7], row[8], row[10], row[11]])
        current_index += 1
    return table_data


@app.route('/download/CSV')
@cross_origin(headers=["Content-Type", "Authorization"])
@requires_auth
def downloadCSVFile():
    cursor.execute(
        "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country")
    rows = cursor.fetchall()
    fp = open(
        '/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv', 'w')
    my_file = csv.writer(fp)
    csv_header = ['countryid', 'genreid', 'actorid', 'showid', 'showname', 'description',
                  'numberofreviews', 'averagerating', 'isdiscontinued', 'actorname', 'genrename', 'countryname']
    my_file.writerow(csv_header)
    my_file.writerows(rows)
    fp.close()
    convert_csv_to_json()
    path = "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv"
    return send_file(path, as_attachment=True)


@app.route('/download/JSON')
@cross_origin(headers=["Content-Type", "Authorization"])
@requires_auth
def downloadJSONFile():
    path = "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.json"
    return send_file(path, as_attachment=True)


@app.route('/profile')
def profile():
    return session['profile']


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port="4000")
