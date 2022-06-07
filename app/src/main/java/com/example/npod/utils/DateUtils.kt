package com.example.npod.utils

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(dayAgo: Int): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .format(getDateWithOffset(dayAgo))
}

private fun getDateWithOffset(dayAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -dayAgo)
    println(calendar.time)

    return calendar.time
}