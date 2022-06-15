package com.example.npod.ui.screens.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.npod.R
import com.example.npod.domain.notes.Note

class NoteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
) {
    fun bind(note: Note) {
        val titleView = itemView.findViewById<TextView>(R.id.note_item_title)
        titleView.text = note.title

        val descriptionView = itemView.findViewById<TextView>(R.id.note_item_description)
        descriptionView.text = note.description
    }
}