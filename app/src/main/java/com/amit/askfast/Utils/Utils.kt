package com.amit.askfast.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

import com.amit.askfast.Model.MovieDetails

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


object Utils {

    const val API_KEY = "419ffa2d21f9e1c4e01ac4e45b4bb959"
    const val POSTER_URL = "https://image.tmdb.org/t/p/w500"
    const val BANNER_URL = "https://image.tmdb.org/t/p/original"

    fun checkInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun convertTimeFormat(yyyy_mm_dd: String?): String {
        Log.d("asdf", "date $yyyy_mm_dd")
        val originalFormat = SimpleDateFormat("yyyy-MM-dd")
        val targetFormat = SimpleDateFormat("dd MMMM yyyy")
        var date: Date? = null
        try {
            date = originalFormat.parse(yyyy_mm_dd)
            Log.d("asdf", "date " + date!!.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val formattedDate = targetFormat.format(date)

        return formattedDate
    }

    fun getGenresToString(genresList: List<MovieDetails.GenresData>): String {
        if (genresList.size > 0) {
            val genres = StringBuilder()
            for (i in genresList.indices) {
                genres.append(genresList[i].name).append(", ")
            }
            return genres.deleteCharAt(genres.length - 2).toString()
        } else
            return "NA"
    }
}
