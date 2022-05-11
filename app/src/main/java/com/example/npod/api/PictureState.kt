package com.example.npod.api

sealed class PictureState {
    data class Success(val response: PictureOfTheDayResponse) : PictureState()
    data class Error (val message: String) : PictureState()
    object Loading : PictureState()
}
