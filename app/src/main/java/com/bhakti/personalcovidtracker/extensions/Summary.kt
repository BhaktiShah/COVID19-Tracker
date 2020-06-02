package com.bhakti.personalcovidtracker.extensions

import com.bhakti.personalcovidtracker.Constants
import com.bhakti.personalcovidtracker.data.JSONCountryDetail
import com.bhakti.personalcovidtracker.data.JSONSummary

fun JSONSummary.getGlobalStats(): String {
    return this.Global.toString()
}

fun JSONSummary.getTopCountries(): List<JSONCountryDetail> {
    val result = this.Countries.sortedByDescending { it.TotalConfirmed }
    val topCountries = result.subList(0, minOf(Constants.COUNTRY_LIST_SIZE, result.size))
    return topCountries
}
