package com.amit.askfast.Model

import java.io.Serializable

class ApiResponse : Serializable {
    var isStatus: Boolean = false
    var message: String? = null
    var popularMovies: PopularMovies? = null
    var movieDetails: MovieDetails? = null
    var castCrew: CastCrew? = null

    constructor(status: Boolean, message: String, popularMovies: PopularMovies) {
        this.isStatus = status
        this.message = message
        this.popularMovies = popularMovies
    }

    constructor(status: Boolean, message: String, movieDetails: MovieDetails) {
        this.isStatus = status
        this.message = message
        this.movieDetails = movieDetails
    }

    constructor(status: Boolean, message: String, castCrew: CastCrew) {
        this.isStatus = status
        this.message = message
        this.castCrew = castCrew
    }

    constructor(status: Boolean, message: String) {
        this.isStatus = status
        this.message = message
    }
}
