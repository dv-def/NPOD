package com.example.npod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.npod.domain.nasa.NasaRepository
import com.example.npod.ui.screens.MainViewModel
import com.example.npod.ui.screens.photos.PhotosViewModel

class NasaViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(repository) as T
        }

        throw IllegalStateException("Can't find ViewModel")
    }
}