package com.example.npod.data

import com.example.npod.domain.models.PictureOfTheDay

sealed class PictureState {
    data class Success(val response: PictureOfTheDay) : PictureState()
    data class Error (val message: String) : PictureState()
    object Loading : PictureState()
}
