package com.example.npod.api

import com.example.npod.data.dto.PictureOfTheDayDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): PictureOfTheDayDTO
}