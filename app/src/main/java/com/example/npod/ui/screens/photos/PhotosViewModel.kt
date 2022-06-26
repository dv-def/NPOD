package com.example.npod.ui.screens.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.AppState
import com.example.npod.domain.nasa.NasaRepository
import com.example.npod.domain.nasa.PhotoMars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class PhotosViewModel(
    private val repository: NasaRepository
) : ViewModel() {
    private val _photosFlow = MutableStateFlow<AppState<List<PhotoMars>>?>(null)
    val photosFlow: Flow<AppState<List<PhotoMars>>?> = _photosFlow

    fun getMarsPhotos(date: String) {
        viewModelScope.launch {
            _photosFlow.emit(AppState.Loading)
            try {
                val result = repository.getMarsPhoto(date)
                _photosFlow.emit(AppState.Success(result))
            } catch (e: IOException) {
                e.printStackTrace()
                _photosFlow.emit(AppState.Error("При загрузке фото произошла ошибка"))
            } catch (e: HttpException) {
                e.printStackTrace()
                _photosFlow.emit(AppState.Error("При запросе произошла ошибка"))
            }
        }

    }
}