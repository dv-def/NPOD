package com.example.npod.data.repository

import com.example.npod.BuildConfig
import com.example.npod.data.Retrofit
import com.example.npod.domain.models.PhotoMars
import com.example.npod.domain.repository.NasaRepository
import com.example.npod.domain.models.PictureOfTheDay
import com.example.npod.mappers.toPhotoMars
import com.example.npod.mappers.toPictureOfTheDay

class NasaRepositoryImpl : NasaRepository {
    override suspend fun getPictureOfTheDay(date: String): PictureOfTheDay {
        return Retrofit.getNasaApi()
            .getPictureOfTheDay(BuildConfig.API_KEY, date)
            .toPictureOfTheDay()
    }

    override suspend fun getMarsPhoto(date: String): List<PhotoMars> {
        return Retrofit
            .getNasaApi()
            .getPhotosMarsList(date, BuildConfig.API_KEY)
            .photos.map { it.toPhotoMars() }
    }
}