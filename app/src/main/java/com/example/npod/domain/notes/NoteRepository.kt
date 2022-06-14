package com.example.npod.domain.notes

interface NoteRepository {
    suspend fun getAllNotes(): List<Note>
    suspend fun save(note: Note): Long
}