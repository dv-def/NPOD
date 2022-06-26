package com.example.npod.ui.screens.notes

sealed class ValidateNoteResult {
    object Valid : ValidateNoteResult()
    data class Invalid(
        val errorType:NoteValidateErrorType,
        val message: String
    ) : ValidateNoteResult()
}

enum class NoteValidateErrorType {
    TITLE_ERROR,
    DESCRIPTION_ERROR
}
