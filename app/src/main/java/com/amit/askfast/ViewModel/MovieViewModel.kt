package com.amit.askfast.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

import com.amit.askfast.Interface.API
import com.amit.askfast.Model.ApiResponse
import com.amit.askfast.Model.CastCrew
import com.amit.askfast.Model.MovieDetails
import com.amit.askfast.Model.PopularMovies
import com.amit.askfast.Utils.APIClient
import com.amit.askfast.Utils.Utils
import com.amit.askfast.repository.Repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private val repo = Repository()

    fun getMoviesList(): LiveData<ApiResponse> = repo.getMoviesList()

    fun getMovieDetails(movieId: String): LiveData<ApiResponse> = repo.getMovieDetails()

    fun loadMovieDetails(movieId: String) = repo.loadMovieDetails(movieId)

    fun loadMovieCastCrew(movieId: String) = repo.loadMovieCastCrew(movieId)

    fun loadMoviesList(type: String) = repo.loadMoviesList(type)
}