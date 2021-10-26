# TVShows
## Otvoreno računarstvo FER
###### Version 1.0

###### Author: Luka Terzić 

This software has an MIT license which is a permissive free software license originating from MIT. 
You can read more about the license in the license file in this repository but if you are lazy you can read a little bit about it here as a quick sumary:

> A short and simple permissive license with conditions only requiring preservation of copyright and license notices. Licensed works, modifications,
> and larger works may be distributed under different terms and without source code.

The language that the data is provided in is **English**.

## CSV

The CSV file has a delimiter ',' and has 12 columns. 
An example of one row in the csv file is: 

countryid | genreid | actorid | showid | showname | description | numberofreviews | averagerating | isdiscontinued | actorname | genrename | countryname 
--------- | ------- | ------- | ------ | -------- | ----------- | --------------- | ------------- | -------------- | --------- | --------- | -----------
1 | 3 | 3 | 1 | The Flash | A fast man | 100 | 3 | f | Grant Gustin | superhero | US

One TV Show can have multiple actors. The way this is implemented in the CSV file is that we repeat the occurrence of the show for the number of times that the show has actors in the data set. For example, if we have 2 actors in a show in our data set (Kaley Cuoco and Jim Parsons) the CSV representation will be:

1,1,1,1,The big bang theory,A woman who moves into an apartment across the hall from two brilliant but socially awkward physicist shows them how little they know about life outside of the laboratory.,158,3,t,Jim Parsons,comedy,US

1,1,2,1,The big bang theory,A woman who moves into an apartment across the hall from two brilliant but socially awkward physicist shows them how little they know about life outside of the laboratory.,158,3,t,Kaley Cuoco,comedy,US

The id's are here for possible future queries in the database since they are the primary keys of the data set.

## JSON 

The JSON file is best to bee seen on an example that has 2 shows. 

```javascript
{
    "shows": [
        {
            "showid": "1", 
            "numberofreviews": "158", 
            "description": "A woman who moves into an apartment across the hall from two brilliant but socially awkward physicist shows them how little they know about life outside of the laboratory.", 
            "isdiscontinued": "t", 
            "genre": {
                "genreid": "1", 
                "genrename": "comedy"
            }, 
            "actors": [
                {
                    "actorid": "1", 
                    "actorname": "Jim Parsons"
                }, 
                {
                    "actorid": "2", 
                    "actorname": "Kaley Cuoco"
                }
            ], 
            "country": {
                "countryname": "US", 
                "countryid": "1"
            }, 
            "showname": "The big bang theory", 
            "averagerating": "3"
        }, 
        {
            "showid": "2", 
            "numberofreviews": "100", 
            "description": "After being struck by lightning, Barry Allen weaks up from his coma to discover he has been given the power of super speed, becoming the next Flash, fighting crime in Central City.", 
            "isdiscontinued": "f", 
            "genre": {
                "genreid": "3", 
                "genrename": "superhero"
            }, 
            "actors": [
                {
                    "actorid": "3", 
                    "actorname": "Grant Gustin"
                }, 
                {
                    "actorid": "4", 
                    "actorname": "Cardice Patton"
                }
            ], 
            "country": {
                "countryname": "US", 
                "countryid": "1"
            }, 
            "showname": "The Flash", 
            "averagerating": "3"
        }, 
    ]
}
```
The atributes are mostly the same as in the CSV file and they are all self documenting. The only difference is that we can get clearer nesting in a JSON that provides better understing and semantics of data. For example we have added a new key "actores" that has a value of an array of actors. We did the same for country and genre which provides the user of the JSON a better understanding of the content that is provided.

## Implementation details

The database was managed with PostgreSQL.

The CSV was generated using the \copy method in psql.

For the JSON I made i python script that converts the generated CSV into the JSON that is provided in the repository.


