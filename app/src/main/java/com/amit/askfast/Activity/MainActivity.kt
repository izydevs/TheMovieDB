package com.amit.askfast.Activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.support.v7.widget.SearchView
import android.widget.Spinner
import android.widget.Toast

import com.amit.askfast.Adapter.MovieAdapter
import com.amit.askfast.Model.ApiResponse
import com.amit.askfast.Model.Movie
import com.amit.askfast.Utils.Utils
import com.amit.askfast.ViewModel.MovieViewModel
import com.amit.askfast.R

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var recyclerView: RecyclerView? = null
    private var progressDialog: ProgressDialog? = null
    private var viewModel: MovieViewModel? = null
    private var mAdapter: MovieAdapter? = null
    private var myList: List<Movie>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        if (Utils.checkInternetConnection(this)) {
            initBindViews()
        } else {
            Toast.makeText(this, resources.getString(R.string.check_internet), Toast.LENGTH_LONG)
                .show()
            finishAffinity()
        }
    }

    override fun onResume() {
        showProgressDialog(applicationContext.resources.getString(R.string.fetch_now_playing_data))
        viewModel!!.loadMoviesList("now_playing")
        super.onResume()
    }

    private fun initBindViews() {
        recyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        val movitDbType = findViewById<Spinner>(R.id.movie_db_type)
        movitDbType.onItemSelectedListener = this
        progressDialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
        showProgressDialog(applicationContext.resources.getString(R.string.fetch_now_playing_data))
        initViewModel()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel!!.getMoviesList().observe(this, Observer { apiResponse ->
            assert(apiResponse != null)
            updateUI(apiResponse!!)
        })
    }


    private fun showProgressDialog(movie_type: String) {
        progressDialog!!.setMessage(movie_type)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun dismissProgressDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }

    private fun updateUI(response: ApiResponse) {
        dismissProgressDialog()
        if (response.isStatus) {
            myList = response.popularMovies!!.results!!
            sortByDate()
        } else {
            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG)
                .show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        search(searchView)
        return true
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter!!.filter.filter(newText)
                return true
            }
        })
    }

    private fun sortByDate() {
        if (myList != null) {
            myList = myList!!.sortedWith(compareBy({ it.release_date })).reversed()
            mAdapter = MovieAdapter(myList!!, this)
            recyclerView!!.adapter = mAdapter
        }
    }

    private fun sortByRating() {
        if (myList != null) {
            myList = myList!!.sortedWith(compareBy({ it.vote_average })).reversed()
            mAdapter = MovieAdapter(myList!!, this)
            recyclerView!!.adapter = mAdapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, i: Int, l: Long) {
        if (i == 0) {
            sortByDate()
        } else if (i == 1) {
            sortByRating()
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {}
}
