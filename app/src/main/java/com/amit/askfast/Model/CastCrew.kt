package com.amit.askfast.Model

class CastCrew {
    var id: Long = 0
    var cast: List<Cast>? = null
    var crew: List<Crew>? = null

    inner class Cast {
        var cast_id: Int = 0
        var character = "NA"
        var credit_id: String? = null
        var gender: Int = 0
        var id: Long = 0
        var name = "NA"
        var order: Int = 0
        var profile_path: String? = null
    }

    inner class Crew{
        var credit_id: String? = null
        var department: String? = null
        var gender: Int = 0
        var id: Long = 0
        var job = "NA"
        var name = "NA"
        var profile_path: String? = null
    }
}
