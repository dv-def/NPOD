package com.example.npod.ui

import com.example.npod.domain.nasa.PhotoMars
import com.example.npod.domain.notes.Note

sealed class AdapterItem {
    class PhotoMarsItem(val photoMars: PhotoMars) : AdapterItem()
    class NoteItem(val note: Note) : AdapterItem()
}