package com.example.npod.data.repository

import com.example.npod.BuildConfig
import com.example.npod.data.Retrofit
import com.example.npod.domain.repository.NasaRepository
import com.example.npod.domain.models.PictureOfTheDay
import com.example.npod.mappers.toPictureOfTheDay

class NasaRepositoryImpl : NasaRepository {
    override suspend fun getPictureOfTheDay(date: String): PictureOfTheDay {
        return Retrofit.getNasaApi()
            .getPictureOfTheDay(BuildConfig.API_KEY, date)
            .toPictureOfTheDay()
    }
}