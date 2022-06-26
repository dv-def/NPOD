package com.example.npod.data.nasa

import com.example.npod.BuildConfig
import com.example.npod.domain.nasa.PhotoMars
import com.example.npod.domain.nasa.NasaRepository
import com.example.npod.domain.nasa.PictureOfTheDay

class NasaRepositoryImpl : NasaRepository {
    override suspend fun getPictureOfTheDay(date: String): PictureOfTheDay {
        return Retrofit.getNasaApi()
            .getPictureOfTheDay(BuildConfig.API_KEY, date)
            .toPictureOfTheDay()
    }

    override suspend fun getMarsPhoto(date: String): List<PhotoMars> {
        return Retrofit.getNasaApi()
            .getPhotosMarsList(date, BuildConfig.API_KEY)
            .photos.map { it.toPhotoMars() }
    }
}