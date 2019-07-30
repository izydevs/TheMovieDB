package com.amit.askfast.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView

import com.amit.askfast.Activity.MovieDetailsActivity
import com.amit.askfast.Model.Movie
import com.amit.askfast.R
import com.amit.askfast.Utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.util.ArrayList

class MovieAdapter(private val myList: List<Movie>, private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>(), Filterable {
    private var myFilteredList: List<Movie>? = null

    init {
        myFilteredList = myList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.movie_item_layout, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val movie = myFilteredList!![i]
        setPosterImage(myViewHolder.posterImage, Utils.POSTER_URL + movie.poster_path)
        myViewHolder.title.text = movie.title
        myViewHolder.summary.text = movie.overview
        myViewHolder.rating.text = movie.vote_average.toString()
        myViewHolder.releaseDate.text = Utils.convertTimeFormat(movie.release_date)
        myViewHolder.language.text = movie.original_language!!.toUpperCase()

    }

    private fun setPosterImage(posterImage: ImageView, poster_url: String) {
        Log.d("asdf", "url is $poster_url")
        Glide.with(context).load(poster_url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.spinner_loader).into(posterImage)
    }

    override fun getItemCount(): Int {
        return myFilteredList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                Log.d("asdf", "search text is $charString")
                if (charString.isEmpty()) {
                    myFilteredList = myList
                } else {
                    val filteredList = ArrayList<Movie>()
                    for (movie in myList) {
                        if (movie.title!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie)
                        }
                    }
                    myFilteredList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = myFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                myFilteredList = filterResults.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var posterImage: ImageView = itemView.findViewById(R.id.poster_iv)
        internal var title: TextView = itemView.findViewById(R.id.title_tv)
        internal var summary: TextView = itemView.findViewById(R.id.movie_desc)
        internal var rating: TextView = itemView.findViewById(R.id.rating)
        internal var releaseDate: TextView = itemView.findViewById(R.id.release_date)
        internal var language: TextView = itemView.findViewById(R.id.lang)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movie_id", myFilteredList!![adapterPosition].id.toString())
            context.startActivity(intent)
        }
    }
}
