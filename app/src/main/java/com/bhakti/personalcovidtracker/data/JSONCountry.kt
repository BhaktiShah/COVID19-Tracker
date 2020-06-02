package com.bhakti.personalcovidtracker.data

import java.util.*

data class JSONCountry(
    val Country: String,
    val CountryCode: String,
    val Lat: String,
    val Lon: String,
    val Confirmed: String,
    val Deaths: String,
    val Recovered: String,
    val Active: String,
    val Date: Date,
    val LocationID: String
)