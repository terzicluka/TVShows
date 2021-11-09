import csv
import json


def convert_csv_to_json():
    csv_file = open(
        "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.csv", "r")
    reader = csv.reader(csv_file)
    next(reader)
    data = []
    actorid = []
    actorname = []
    counter = 0
    countryid = NotImplemented
    genreid = NotImplemented
    showid = NotImplemented
    showname = NotImplemented
    description = NotImplemented
    numberofreviews = NotImplemented
    averagerating = NotImplemented
    isdiscontinued = NotImplemented
    genrename = NotImplemented
    countryname = NotImplemented

    for row in reader:
        if counter % 2 == 0:
            countryid = row[0]
            genreid = row[1]
            showid = row[3]
            showname = row[4]
            description = row[5]
            numberofreviews = row[6]
            averagerating = row[7]
            isdiscontinued = row[8]
            genrename = row[10]
            countryname = row[11]
            actorname.append(row[9])
            actorid.append(row[2])
            counter += 1
        else:
            actorname.append(row[9])
            actorid.append(row[2])
            actors = [{"actorid": actorid[0], "actorname": actorname[0]}, {
                "actorid": actorid[1], "actorname": actorname[1]}]
            genre = {"genreid": genreid, "genrename": genrename}
            country = {"countryid": countryid, "countryname": countryname}
            data.append({
                "showid": showid,
                "showname": showname,
                "description": description,
                "numberofreviews": numberofreviews,
                "averagerating": averagerating,
                "isdiscontinued": isdiscontinued,
                "actors": actors,
                "country": country,
                "genre": genre
            })
            actorid = []
            actorname = []
            counter += 1
    csv_file.close()

    json_file = open(
        "/Users/lukaterzic/Documents/FER/or-labosi/TVShows/WEBCode/TVShows.json", "w")
    json.dump({"shows": data}, json_file, indent=4)
    json_file.close()
    # return json.dumps({"shows": data})
