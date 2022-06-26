package com.example.npod

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.npod.data.notes.NoteDAO
import com.example.npod.data.notes.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    companion object {
        const val DB_NAME = "npod"
    }
}