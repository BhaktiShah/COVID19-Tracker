package com.bhakti.personalcovidtracker.data

data class JSONGlobal(
    val NewConfirmed: Long,
    val TotalConfirmed: Long,
    val NewDeaths: Long,
    val TotalDeaths: Long,
    val NewRecovered: Long,
    val TotalRecovered: Long
)