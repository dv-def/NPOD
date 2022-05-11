package com.example.npod.domain

import com.example.npod.BuildConfig
import com.example.npod.api.PictureOfTheDayResponse
import com.example.npod.api.Retrofit

class NasaRepositoryImpl : NasaRepository {
    override suspend fun getPictureOfTheDay(): PictureOfTheDayResponse {
        return Retrofit.getNasaApi().getPictureOfTheDay(BuildConfig.API_KEY)
    }
}