package com.example.npod.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.AppState
import com.example.npod.domain.nasa.NasaRepository
import com.example.npod.domain.nasa.PictureOfTheDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NasaRepository) : ViewModel() {
    private val _pictureFlow: MutableStateFlow<AppState<PictureOfTheDay>?> = MutableStateFlow(null)
    val pictureFlow: Flow<AppState<PictureOfTheDay>?> = _pictureFlow

    fun getPictureOfTheDay(date: String) {
        viewModelScope.launch {
            _pictureFlow.emit(AppState.Loading)
            try {
                _pictureFlow.emit(AppState.Success(repository.getPictureOfTheDay(date)))
            } catch (e: Exception) {
                _pictureFlow.emit(AppState.Error("Не удалось загрузить изображение"))
            }
        }
    }
}