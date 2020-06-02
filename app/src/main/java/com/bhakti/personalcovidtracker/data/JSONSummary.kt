package com.bhakti.personalcovidtracker.data

import java.util.*

data class JSONSummary(val Global: JSONGlobal, val Countries: List<JSONCountryDetail>, val Date: Date)