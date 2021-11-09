import psycopg2
from flask import Flask, render_template, send_file, request
import csv
from CSVJSONConverter import convert_csv_to_json


app = Flask(__name__, template_folder='template')

connection = psycopg2.connect(
    host="localhost",
    database="TVShows",
    user="postgres",
    password="kikoniko1",
    port="5433"
)

cursor = connection.cursor()


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


@ app.route('/download/CSV')
def downloadCSVFile():
    path = "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv"
    return send_file(path, as_attachment=True)


@ app.route('/download/JSON')
def downloadJSONFile():
    path = "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.json"
    return send_file(path, as_attachment=True)


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
