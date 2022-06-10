package com.example.npod.data.dto

import com.example.npod.domain.models.PictureOfTheDay
import com.google.gson.annotations.SerializedName
import java.util.*

data class PictureOfTheDayDTO(
    @SerializedName("copyright")
    val copyright: String?,

    @SerializedName("date")
    val date: Date,

    @SerializedName("explanation")
    val explanation: String,

    @SerializedName("hdurl")
    val hdurl: String,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("service_version")
    val serviceVersion: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String
)

fun PictureOfTheDayDTO.toPictureOfTheDay(): PictureOfTheDay = PictureOfTheDay(
    copyright = this.copyright ?: "",
    date = this.date,
    explanation = this.explanation,
    hdurl = this.hdurl,
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url
)