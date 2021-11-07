import psycopg2
from flask import Flask
import csv
from CSVJSONConverter import convert_csv_to_json

app = Flask(__name__)

# connect to a database
connection = psycopg2.connect(
    host="localhost",
    database="TVShows",
    user="postgres",
    password="kikoniko1",
    port="5433"
)

cursor = connection.cursor()

# EXAMPLE OF HTTP request handling in flask
# connection.close() ako zelim zatvoriti konekciju, bas i ne zelim jer server mora stalno moći prikazivati podatke.


@app.route('/')
def shows():
    cursor.execute(
        "SELECT country.countryid, genre.genreid, actor.actorid, shows.showid, shows.showname, shows.description, shows.numberofreviews, shows.averagerating, shows.isdiscontinued, actor.actorname, genre.genrename, country.countryname from shows natural join showactor natural join actor natural join genre natural join country")
    rows = cursor.fetchall()
    fp = open(
        '/Users/lukaterzic/Documents/FER/or-labosi/TVShows/Backend/TVShows.csv', 'w')
    my_file = csv.writer(fp)
    csv_header = ['countryid', 'genreid', 'actorid', 'showid', 'showname', 'description',
                  'numberofreviews', 'averagerating', 'isdiscontinued', 'actorname', 'genrename', 'countryname']
    my_file.writerow(csv_header)
    my_file.writerows(rows)
    fp.close()
    data = convert_csv_to_json()
    return data


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
