package com.amit.askfast.Model

import java.io.Serializable

class Movie : Serializable {
    var vote_count: Long = 0
    var id: Int = 0
    var isVideo: Boolean = false
    var vote_average: Double = 0.toDouble()
    var title: String? = null
    var popularity: Double = 0.toDouble()
    var poster_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var genre_ids: List<Int>? = null
    var backdrop_path: String? = null
    var isAdult: Boolean = false
    var overview: String? = null
    var release_date: String? = null
}