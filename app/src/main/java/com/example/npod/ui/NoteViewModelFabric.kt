package com.example.npod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.npod.domain.notes.NoteRepository
import com.example.npod.ui.screens.notes.NoteViewModel

class NoteViewModelFabric(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }

        throw IllegalStateException("Can't find ViewModel")
    }
}