package com.example.npod.domain

import com.example.npod.api.PictureOfTheDayResponse

interface NasaRepository {
    suspend fun getPictureOfTheDay(): PictureOfTheDayResponse
}