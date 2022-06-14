package com.example.npod.data.nasa.pod

import com.example.npod.domain.nasa.PictureOfTheDay

sealed class PictureState {
    data class Success(val response: PictureOfTheDay) : PictureState()
    data class Error (val message: String) : PictureState()
    object Loading : PictureState()
}
