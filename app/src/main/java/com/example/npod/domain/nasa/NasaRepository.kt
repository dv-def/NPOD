package com.example.npod.domain.nasa

interface NasaRepository {
    suspend fun getPictureOfTheDay(date: String): PictureOfTheDay
    suspend fun getMarsPhoto(date: String): List<PhotoMars>
}