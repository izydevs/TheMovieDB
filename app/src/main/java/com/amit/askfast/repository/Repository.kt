package com.amit.askfast.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.amit.askfast.Interface.API
import com.amit.askfast.Model.ApiResponse
import com.amit.askfast.Model.CastCrew
import com.amit.askfast.Model.MovieDetails
import com.amit.askfast.Model.PopularMovies
import com.amit.askfast.Utils.APIClient
import com.amit.askfast.Utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository {
    private var myMoviesList: MutableLiveData<ApiResponse>? = MutableLiveData()

    private var myMovieDetails: MutableLiveData<ApiResponse>? = MutableLiveData()

    fun getMoviesList(): LiveData<ApiResponse> = myMoviesList!!

    fun getMovieDetails(): LiveData<ApiResponse> = myMovieDetails!!

    fun loadMovieDetails(movieId: String) {
        val api = APIClient.client!!.create(API::class.java)
        val call = api.getMovieDetails(movieId, Utils.API_KEY)
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                Log.d("asdfg", "success " + response.body()!!)
                if (response.body() != null) {
                    myMovieDetails!!.postValue(
                        ApiResponse(
                            true,
                            "movie_details",
                            response.body()!!
                        )
                    )
                } else {
                    myMovieDetails!!.postValue(ApiResponse(false, "something went wrong"))
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d("asdfg", "failure $t")
                myMovieDetails!!.postValue(ApiResponse(false, "failure"))
            }
        })

    }

    fun loadMovieCastCrew(movieId: String) {
        val api = APIClient.client!!.create(API::class.java)
        val call = api.getMovieCastCrew(movieId, Utils.API_KEY)
        call.enqueue(object : Callback<CastCrew> {
            override fun onResponse(call: Call<CastCrew>, response: Response<CastCrew>) {
                Log.d("asdfg", "success " + response.body()!!)
                if (response.body() != null) {
                    myMovieDetails!!.postValue(ApiResponse(true, "cast_crew", response.body()!!))
                } else {
                    myMovieDetails!!.postValue(ApiResponse(false, "something went wrong"))
                }
            }

            override fun onFailure(call: Call<CastCrew>, t: Throwable) {
                Log.d("asdfg", "failure $t")
                //myMovieDetails.postValue(new ApiResponse(false,"failure"));
            }
        })

    }

    fun loadMoviesList(movieDbType: String) {
        val api = APIClient.client!!.create(API::class.java)
        val call = api.getPopularMovies(movieDbType, Utils.API_KEY)
        call.enqueue(object : Callback<PopularMovies> {
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                Log.d("asdf", "success " + response.body()!!)
                if (response.body() != null) {
                    myMoviesList!!.postValue(ApiResponse(true, "success", response.body()!!))
                } else {
                    myMoviesList!!.postValue(ApiResponse(false, "something went wrong"))
                }
            }

            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                myMoviesList!!.postValue(ApiResponse(false, "failure"))
            }
        })
    }
}