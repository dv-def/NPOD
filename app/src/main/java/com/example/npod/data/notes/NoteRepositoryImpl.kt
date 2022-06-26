package com.example.npod.data.notes

import com.example.npod.domain.notes.Note
import com.example.npod.domain.notes.NoteRepository
import com.example.npod.domain.notes.toEntity

class NoteRepositoryImpl(private val noteDAO: NoteDAO) : NoteRepository {
    override suspend fun getAllNotes(): List<Note> {
        return noteDAO.getNotes().map { it.toNote() }
    }

    override suspend fun save(note: Note): Long {
        return noteDAO.insertNote(note.toEntity())
    }

    override suspend fun delete(note: Note): Int {
        return noteDAO.delete(note.toEntity())
    }
}