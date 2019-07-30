package com.amit.askfast.Model

class PopularMovies {
    var page: Int = 0
    var total_results: Long = 0
    var total_pages: Int = 0
    private val dates: Dates? = null
    var results: List<Movie>? = null

    private inner class Dates {
        var maximum: String? = null
        var minimum: String? = null
    }
}
