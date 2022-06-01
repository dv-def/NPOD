package com.example.npod.mappers

import com.example.npod.data.dto.PhotoMarsDTO
import com.example.npod.domain.models.PhotoMars

fun PhotoMarsDTO.toPhotoMars(): PhotoMars = PhotoMars (
    img = img,
    date = date
)