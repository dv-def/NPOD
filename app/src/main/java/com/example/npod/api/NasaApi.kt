package com.example.npod.api

import com.example.npod.data.dto.PhotoMarsListDTO
import com.example.npod.data.dto.PictureOfTheDayDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): PictureOfTheDayDTO

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPhotosMarsList(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String
    ): PhotoMarsListDTO
}