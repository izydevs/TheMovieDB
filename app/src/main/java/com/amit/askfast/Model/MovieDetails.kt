package com.amit.askfast.Model

import java.io.Serializable

class MovieDetails : Serializable {
    var isAdult: Boolean = false
    var backdrop_path: String? = null
    var belongs_to_collection: BelongsToCollection? = null
    var budget: Long = 0
    var genres: List<GenresData>? = null
    var homepage: String? = null
    var id: Int = 0
    var imdb_id: String? = null
    var original_language: String? = null
    var overview: String? = null
    var popularity: Double = 0.toDouble()
    var poster_path: String? = null
    var production_companies: List<ProductionCompanies>? = null
    var production_countries: List<ProductionCountries>? = null
    var release_date: String? = null
    var revenue: Long = 0
    var runtime: Int = 0
    var spoken_languages: List<SpokenLanguages>? = null
    var status: String? = null
    var tagline: String? = null
    var title: String? = null
    var isVideo: Boolean = false
    var vote_average: Double = 0.toDouble()
    var vote_count: Long = 0

    inner class BelongsToCollection {
        var id: Long? = 0
        var name: String?= null
        var poster_path: String?=null
        var backdrop_path: String?=null
    }

    inner class GenresData {
        var id: Long = 0
        var name: String?=null
    }

    inner class ProductionCompanies {
        var id: Int = 0
        var logo_path: String? = null
        var name: String? = null
        var origin_country: String? = null
    }

    inner class ProductionCountries {
        var iso_3166_1: String? = null
        var name: String? = null
    }

    inner class SpokenLanguages {
        var iso_639_1: String? = null
        var name: String? = null
    }
}
