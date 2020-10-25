package com.amit.askfast.Activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*

import com.amit.askfast.Adapter.CastAdapter
import com.amit.askfast.Model.ApiResponse
import com.amit.askfast.Model.MovieDetails
import com.amit.askfast.Utils.Utils
import com.amit.askfast.ViewModel.MovieViewModel
import com.amit.askfast.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.text.MessageFormat

class MovieDetailsActivity : AppCompatActivity() {

    private var movieId: String? = null
    private var overview: TextView? = null
    private var duration: TextView? = null
    private var releaseDate: TextView? = null
    private var rating: TextView? = null
    private var genres: TextView? = null
    private var language: TextView? = null
    private var poster: ImageView? = null
    private var recyclerView: RecyclerView? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        if (intent != null && intent.hasExtra("movie_id")) {
            movieId = intent.getStringExtra("movie_id")
            Log.d("asdfg", "movie id " + movieId!!)
        }
        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (Utils.checkInternetConnection(this)) {
            initBindViews()
        } else {
            Toast.makeText(this, resources.getString(R.string.check_internet), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun initBindViews() {
        overview = findViewById(R.id.overview)
        duration = findViewById(R.id.duration)
        releaseDate = findViewById(R.id.release_date)
        rating = findViewById(R.id.rating)
        genres = findViewById(R.id.genres)
        language = findViewById(R.id.lang)
        poster = findViewById(R.id.banner_poster)
        recyclerView = findViewById(R.id.cast_rv)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        progressDialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        showProgressDialog()
        initViewModel()

    }

    private fun initViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovieDetails(movieId!!).observe(this, Observer { apiResponse ->
            assert(apiResponse != null)
            updateUI(apiResponse!!)
        })
        viewModel.loadMovieDetails(movieId!!)
        viewModel.loadMovieCastCrew(movieId!!)
    }

    private fun showProgressDialog() {
        progressDialog!!.setMessage(applicationContext.resources.getString(R.string.movie_detail))
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun dismissProgressDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }

    private fun updateUI(response: ApiResponse) {
        dismissProgressDialog()
        if (response.isStatus && response.message == "movie_details") {
            showMovieDetails(response.movieDetails)
        } else if (response.isStatus && response.message == "cast_crew") {
            val mAdapter = CastAdapter(response.castCrew!!.cast!!, this)
            recyclerView!!.adapter = mAdapter
        } else {
            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun showMovieDetails(details: MovieDetails?) {
        if (details != null) {
            supportActionBar!!.title = details.title
            overview!!.text = details.overview
            duration!!.text = MessageFormat.format("{0} minutes", details.runtime)
            releaseDate!!.text = Utils.convertTimeFormat(details.release_date)
            rating!!.text = details.vote_average.toString()
            genres!!.text = Utils.getGenresToString(details.genres!!)
            language!!.text = details.original_language!!.toUpperCase()
            setPosterImage(details.backdrop_path)
        }
    }

    private fun setPosterImage(posterUrl: String?) {
        if (posterUrl != null) {
            Glide.with(this)
                .load(Utils.BANNER_URL + posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.spinner_loader).into(poster!!)
        } else {
            poster!!.setImageDrawable(resources.getDrawable(R.drawable.image_not_available))
        }
    }

}
