package com.bhakti.personalcovidtracker.extensions

import java.text.NumberFormat
import java.util.*

fun Long.getFormattedString() = NumberFormat.getNumberInstance(Locale.getDefault()).format(this)

fun Int.getFormattedString() = NumberFormat.getNumberInstance(Locale.getDefault()).format(this)

fun Float.getFormattedString() = NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
