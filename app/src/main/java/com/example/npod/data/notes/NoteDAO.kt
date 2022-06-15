package com.example.npod.data.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDAO {
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    suspend fun getNotes(): List<NoteEntity>

    @Insert
    suspend fun insertNote(note: NoteEntity): Long

    @Delete
    suspend fun delete(note: NoteEntity): Int
}