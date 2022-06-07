package com.example.npod.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.PhotoState
import com.example.npod.domain.repository.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class PhotosViewModel(
    private val repository: NasaRepository
) : ViewModel() {
    private val _photosFlow = MutableStateFlow<PhotoState?>(null)
    val photosFlow: Flow<PhotoState?> = _photosFlow

    fun getMarsPhotos(date: String) {
        viewModelScope.launch {
            _photosFlow.emit(PhotoState.Loading)
            try {
                val result = repository.getMarsPhoto(date)
                _photosFlow.emit(PhotoState.Success(result))
            } catch (e: IOException) {
                e.printStackTrace()
                _photosFlow.emit(PhotoState.Error("При загрузке фото произошла ошибка"))
            } catch (e: HttpException) {
                e.printStackTrace()
                _photosFlow.emit(PhotoState.Error("При запросе произошла ошибка"))
            }
        }

    }
}