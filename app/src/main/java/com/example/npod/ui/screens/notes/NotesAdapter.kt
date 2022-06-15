package com.example.npod.ui.screens.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.npod.domain.notes.Note

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(notesList: List<Note>) {
        notes.clear()
        notes.addAll(notesList)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = notes[position]
}