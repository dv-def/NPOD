package com.example.npod.data.nasa.photo

import com.example.npod.domain.nasa.PhotoMars

sealed class PhotoState {
    data class Success(val photos: List<PhotoMars>) : PhotoState()
    data class Error(val message: String) : PhotoState()
    object Loading : PhotoState()
}