package com.example.npod.data

import com.example.npod.domain.models.PhotoMars

sealed class PhotoState {
    data class Success(val photos: List<PhotoMars>) : PhotoState()
    data class Error(val message: String) : PhotoState()
    object Loading : PhotoState()
}