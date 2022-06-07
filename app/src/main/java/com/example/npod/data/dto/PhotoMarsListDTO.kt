package com.example.npod.data.dto

import com.google.gson.annotations.SerializedName

data class PhotoMarsListDTO(
    @SerializedName("photos")
    val photos: List<PhotoMarsDTO>
)

data class PhotoMarsDTO(
    @SerializedName("img_src")
    val img: String,

    @SerializedName("earth_date")
    val date: String
)
