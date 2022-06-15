package com.example.npod.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.AppState
import com.example.npod.domain.notes.Note
import com.example.npod.domain.notes.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _editNoteFlow = MutableStateFlow<AppState<Long>?>(null)
    val editNoteFlow: StateFlow<AppState<Long>?> = _editNoteFlow

    private val _noteListFlow = MutableStateFlow<AppState<List<Note>>?>(null)
    val noteListFlow: StateFlow<AppState<List<Note>>?> = _noteListFlow

    private val _noteDeleteFlow = MutableStateFlow<AppState<Int>?>(null)
    val noteDeleteFlow: StateFlow<AppState<Int>?> = _noteDeleteFlow

    fun getAllNotes() {
        _noteListFlow.value = AppState.Loading
        viewModelScope.launch {
            try {
                _noteListFlow.emit(AppState.Success(repository.getAllNotes()))
            } catch (e: Exception) {
                _noteListFlow.emit(AppState.Error("Не удалось загрузить заметки"))
            }
        }
    }

    fun validate(currentNoteState: CurrentNoteState): ValidateNoteResult {
        if (currentNoteState.title.isNullOrBlank()) {
            return ValidateNoteResult.Invalid(
                errorType = NoteValidateErrorType.TITLE_ERROR,
                message = "Укажите заголовок"
            )
        }

        if (currentNoteState.description.isNullOrBlank()) {
            return ValidateNoteResult.Invalid(
                errorType = NoteValidateErrorType.DESCRIPTION_ERROR,
                message = "Заполните описание"
            )
        }

        return ValidateNoteResult.Valid
    }

    fun saveNote(note: Note) {
        _editNoteFlow.value = AppState.Loading
        viewModelScope.launch {
            val result = repository.save(note)
            if (result >= 1L) {
                _editNoteFlow.emit(AppState.Success(result))
            } else {
                _editNoteFlow.emit(AppState.Error("Не удалось сохранить заметку"))
            }
        }
    }

    fun delete(note: Note, position: Int) {
        _noteDeleteFlow.value = AppState.Loading
        viewModelScope.launch {
            try {
                if (repository.delete(note) > 0) {
                    _noteDeleteFlow.emit(AppState.Success(position))
                } else {
                    _noteDeleteFlow.emit(AppState.Error("Заметка для удаления не найдена"))
                }
            } catch (e: Exception) {
                _noteDeleteFlow.emit(AppState.Error("Не удалось удалить заметку"))
            }
        }
    }

    fun itemDeleted() {
        _noteDeleteFlow.value = null
    }

}