package com.example.npod.mappers

import com.example.npod.data.dto.PictureOfTheDayDTO
import com.example.npod.domain.models.PictureOfTheDay

fun PictureOfTheDayDTO.toPictureOfTheDay(): PictureOfTheDay = PictureOfTheDay(
    copyright = copyright,
    date = date,
    explanation = explanation,
    hdurl = hdurl,
    mediaType = mediaType,
    serviceVersion = serviceVersion,
    title = title,
    url = url
)