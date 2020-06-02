package com.bhakti.personalcovidtracker.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getDisplayString(): String {
    val formatter = SimpleDateFormat("E, dd MMM Y, hh:mm a", Locale.getDefault())
    return formatter.format(this).toString()
}