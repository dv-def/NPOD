package com.example.npod.domain.nasa

import java.util.*

data class PictureOfTheDay(
    val copyright: String,
    val date: Date,
    val explanation: String,
    val hdurl: String,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
)

enum class MediaType(val value: String) {
    IMAGE("image"),
    VIDEO("video")
}