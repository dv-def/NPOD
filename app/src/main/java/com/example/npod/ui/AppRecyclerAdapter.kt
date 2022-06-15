package com.example.npod.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.npod.ui.screens.notes.NoteViewHolder
import com.example.npod.ui.screens.photos.PhotoMarsViewHolder

class AppRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_PHOTO_MARS = 1
        private const val TYPE_NOTE = 2
    }

    private val data = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            TYPE_PHOTO_MARS -> PhotoMarsViewHolder(parent)
            TYPE_NOTE -> NoteViewHolder(parent)
            else -> throw IllegalStateException("Unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is PhotoMarsViewHolder -> {
                val photo = (data[position] as AdapterItem.PhotoMarsItem).photoMars
                holder.bind(photo)
            }
            is NoteViewHolder -> {
                val note = (data[position] as AdapterItem.NoteItem).note
                holder.bind(note)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = when(data[position]) {
        is AdapterItem.PhotoMarsItem -> TYPE_PHOTO_MARS
        is AdapterItem.NoteItem -> TYPE_NOTE
    }

    fun setData(items: List<AdapterItem>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
}