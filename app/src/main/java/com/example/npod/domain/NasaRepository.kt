package com.example.npod.domain

import com.example.npod.domain.models.PhotoMars
import com.example.npod.domain.models.PictureOfTheDay

interface NasaRepository {
    suspend fun getPictureOfTheDay(date: String): PictureOfTheDay
    suspend fun getMarsPhoto(date: String): List<PhotoMars>
}