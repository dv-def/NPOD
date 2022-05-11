package com.example.npod.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.npod.api.PictureState
import com.example.npod.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: NasaRepository) : ViewModel() {
    private val _pictureFlow: MutableStateFlow<PictureState?> = MutableStateFlow(null)
    val pictureFlow: Flow<PictureState?> = _pictureFlow

    fun getPictureOfTheDay() {
        viewModelScope.launch {
            _pictureFlow.emit(PictureState.Loading)
            try {
                _pictureFlow.emit(PictureState.Success(repository.getPictureOfTheDay()))
            } catch (e: IOException) {
                _pictureFlow.emit(PictureState.Error("Не удалось загрузить изображение"))
            }
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}