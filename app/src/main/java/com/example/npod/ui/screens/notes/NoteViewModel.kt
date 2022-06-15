package com.example.npod.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.npod.data.AppState
import com.example.npod.domain.notes.Note
import com.example.npod.domain.notes.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _editNoteFlow = MutableStateFlow<AppState<Long>?>(null)
    val editNoteFlow: StateFlow<AppState<Long>?> = _editNoteFlow

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
            val result = noteRepository.save(note)
            if (result >= 1L) {
                _editNoteFlow.emit(AppState.Success(result))
            } else {
                _editNoteFlow.emit(AppState.Error("Не удалось сохранить заметку"))
            }
        }
    }


}