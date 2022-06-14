package com.example.npod.data.notes

sealed class NoteState {
    object Success : NoteState()
    data class Error(val message: String) : NoteState()
    object Loading : NoteState()
}