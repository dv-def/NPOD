package com.example.npod.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.nasa.pod.PictureState
import com.example.npod.domain.nasa.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {
    private val _pictureFlow: MutableStateFlow<PictureState?> = MutableStateFlow(null)
    val pictureFlow: Flow<PictureState?> = _pictureFlow

    fun getPictureOfTheDay(date: String) {
        viewModelScope.launch {
            _pictureFlow.emit(PictureState.Loading)
            try {
                _pictureFlow.emit(PictureState.Success(repository.getPictureOfTheDay(date)))
            } catch (e: IOException) {
                _pictureFlow.emit(PictureState.Error("Не удалось загрузить изображение"))
            }
        }
    }
}