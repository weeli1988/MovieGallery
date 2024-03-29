package com.example.moviegallery.utils

import com.example.moviegallery.data.entity.Movie
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

object JsonConverter {

    //"Title": "Captain Marvel",
    //"Year": "2019",
    //"imdbID": "tt4154664",
    //"Type": "movie",
    //"Poster":
    fun convertToMovieList(jsonObject: JsonObject) : List<Movie> {
        val movieArrayList = ArrayList<Movie>()
        val jsonArray = jsonObject.getAsJsonArray("Search")

        for (arr in jsonArray) {
            val element = arr.asJsonObject
            val movieName = element.get("Title").asString
            val movieYear = element.get("Year").asString
            val movieImdb = element.get("imdbID").asString
            val movieType = element.get("Type").asString
            val moviePoster = element.get("Poster").asString
            val movie = Movie(movieName, movieType, movieYear, movieImdb, moviePoster)
            movieArrayList.add(movie)
        }
        return movieArrayList.toList()
    }

    //    "Title": "Captain Marvel",
    //    "Year": "2019",
    //    "Rated": "PG-13",
    //    "Released": "08 Mar 2019",
    //    "Runtime": "123 min",
    //    "Genre": "Action, Adventure, Sci-Fi",
    //    "Director": "Anna Boden, Ryan Fleck",
    //    "Writer": "Anna Boden, Ryan Fleck, Geneva Robertson-Dworet",
    //    "Actors": "Brie Larson, Samuel L. Jackson, Ben Mendelsohn",
    //    "Plot": "Carol Danvers becomes one of the universe's most powerful heroes when Earth is caught in the middle of a galactic war between two alien races.",
    //    "Language": "English",
    //    "Country": "United States, Australia",
    //    "Awards": "10 wins & 56 nominations",
    //    "Poster": "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_SX300.jpg",
    //    "Ratings": [
    //        {
    //            "Source": "Internet Movie Database",
    //            "Value": "6.8/10"
    //        },
    //        {
    //            "Source": "Rotten Tomatoes",
    //            "Value": "79%"
    //        },
    //        {
    //            "Source": "Metacritic",
    //            "Value": "64/100"
    //        }
    //    ],
    //    "Metascore": "64",
    //    "imdbRating": "6.8",
    //    "imdbVotes": "608,259",
    //    "imdbID": "tt4154664",
    //    "Type": "movie",
    //    "DVD": "28 May 2019",
    //    "BoxOffice": "$426,829,839",
    //    "Production": "N/A",
    //    "Website": "N/A",
    //    "Response": "True"

    fun convertToString(movieObject: JsonObject) : String {

        val content = movieObject.toString()

        val obj = JSONObject(content)

        val stringMap : MutableMap<String, String> = mutableMapOf()

        obj.keys().forEach {
            if (it == "Ratings") {
                val ratingKey = it.plus(": \n")
                var ratingVal = ""
                val jsonArr : JSONArray = obj.get(it) as JSONArray
                for (i in 0 until jsonArr.length()) {
                    val newJsonObj = jsonArr[i] as JSONObject
                    val source = newJsonObj.get("Source").toString()
                    val value = newJsonObj.get("Value").toString()
                    ratingVal = if (i == jsonArr.length() - 1)
                        ratingVal.plus(source).plus(" - ").plus(value)
                    else
                        ratingVal.plus(source).plus(" - ").plus(value).plus("\n")
                }
                stringMap[ratingKey] = ratingVal
            } else {
                stringMap[it.plus(": ")] = obj.get(it).toString()
            }
        }

        var assay = ""

        for (key in stringMap.keys) {
            if (key.contains("Poster") || key.contains("Response") || key.contains("imdbID"))
                continue
            val line = key.plus(stringMap[key]).plus("\n")
            assay += line
        }

        return assay
    }
}