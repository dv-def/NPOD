package com.example.npod.domain.repository

import com.example.npod.domain.models.PictureOfTheDay

interface NasaRepository {
    suspend fun getPictureOfTheDay(date: String): PictureOfTheDay
}