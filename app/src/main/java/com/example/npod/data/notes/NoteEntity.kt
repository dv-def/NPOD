package com.example.npod.data.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.npod.domain.notes.Note

@Entity(tableName = NoteEntity.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey
    val id: Long? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

) {
    companion object {
        const val TABLE_NAME = "notes"
    }
}

fun NoteEntity.toNote() = Note(
    id = this.id,
    title = this.title,
    description = this.description,
)
