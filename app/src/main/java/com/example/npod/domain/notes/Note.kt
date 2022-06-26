package com.example.npod.domain.notes

import com.example.npod.data.notes.NoteEntity

data class Note (
    val id: Long? = null,
    val title: String,
    val description: String,
)

fun Note.toEntity() = NoteEntity (
    id = this.id,
    title = this.title,
    description = this.description,
)
