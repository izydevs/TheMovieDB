package com.amit.askfast.Interface


import com.amit.askfast.Model.CastCrew
import com.amit.askfast.Model.MovieDetails
import com.amit.askfast.Model.PopularMovies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("{movie_db_type}")
    fun getPopularMovies(@Path("movie_db_type") movieDbType: String, @Query("api_key") API_KEY: String): Call<PopularMovies>

    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: String, @Query("api_key") API_KEY: String): Call<MovieDetails>

    @GET("{movie_id}/credits")
    fun getMovieCastCrew(@Path("movie_id") id: String, @Query("api_key") API_KEY: String): Call<CastCrew>

    companion object {

         const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }


}