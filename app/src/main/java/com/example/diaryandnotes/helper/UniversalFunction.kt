package com.example.diaryandnotes.helper

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun unixToDateTimePair(unix: Long): Pair<String, String> {
    val instant = Instant.ofEpochSecond(unix)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")
    val splitter = dateTime.format(formatter).split(" ")
    return Pair(splitter[0], splitter[1])
}