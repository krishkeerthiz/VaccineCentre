package com.yourapp.vaccinecentre.schemamodel

import java.io.Serializable
import com.squareup.moshi.Json

data class SessionsModel(
    var sessions : List<Session>?,

) : Serializable

data class Session(
    @Json(name = "centre_id")
    var centreId : Int = 0,

    var name : String,

    @Json(name = "name_1")
    var name1 : String? = null,

    var address : String? = null,

    @Json(name = "address_1")
    var address1 : String? = null,

    @Json(name = "state_name")
    var stateName : String,

    @Json(name = "state_name_1")
    var stateName1 : String? = null,

    @Json(name = "district_name")
    var districtName : String,

    @Json(name = "district_name_1")
    var districtName1 : String? = null,

    @Json(name = "block_name")
    var blockName : String,

    @Json(name = "block_name_1")
    var blockName1 : String? = null,

    var pincode : String,

    var lat : Float = 0.0f,

    var long : Float = 0.0f,

    var from : String,

    var to : String,

    @Json(name = "fee_type")
    var feeType : String,

    var fee : String,

    @Json(name = "session_id")
    var sessionId : String,

    var date : String,

    @Json(name = "available_capacity")
    var availableCapacity : Int = 0,

    @Json(name = "available_capacity_dose1")
    var availableCapacityDose1 : Int = 0,

    @Json(name = "available_capacity_dose2")
    var availableCapacityDose2 : Int = 0,

    @Json(name = "min_age_limit")
    var minAgeLimit : Int = 0,

    var vaccine : String,

    var slots : List<String>?,

) : Serializable