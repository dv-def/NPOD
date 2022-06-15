package com.example.npod.ui.screens.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.npod.R
import com.example.npod.domain.notes.Note

class NoteViewHolder(
    parent: ViewGroup,
    private var size: Int,
    private val onClickDeleteNote: ((note: Note, position: Int) -> Unit)? = null,
    private val onClickMove: ((from: Int, to: Int) -> Unit)? = null
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
) {
    fun bind(note: Note) {
        itemView.findViewById<TextView>(R.id.note_item_title).apply {
            text = adapterPosition.toString()
        }

        itemView.findViewById<TextView>(R.id.note_item_description).apply {
            text = note.description
        }

        itemView.findViewById<ImageView>(R.id.note_item_btn_delete).apply {
            setOnClickListener {
                if (size != 0) {
                    size -= 1
                }
                onClickDeleteNote?.invoke(note, adapterPosition)
            }
        }

        itemView.findViewById<ImageView>(R.id.note_item_btn_up).apply {
            visibility = if (adapterPosition - 1 < 0) View.GONE else View.VISIBLE
            setOnClickListener {
                onClickMove?.invoke(adapterPosition, adapterPosition - 1)
            }
        }

        itemView.findViewById<ImageView>(R.id.note_item_btn_down).apply {
            visibility = if (adapterPosition + 1 >= size) View.GONE else View.VISIBLE
            setOnClickListener {
                onClickMove?.invoke(adapterPosition, adapterPosition + 1)
            }
        }
    }
}