package com.example.npod.data

import com.example.npod.BuildConfig
import com.example.npod.data.dto.toPhotoMars
import com.example.npod.data.dto.toPictureOfTheDay
import com.example.npod.domain.models.PhotoMars
import com.example.npod.domain.NasaRepository
import com.example.npod.domain.models.PictureOfTheDay

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