package com.tryden.moovi.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Formats the response string to correct date format
 */
fun formatReleaseDate(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse(date, formatter)

    return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")).toString()
}