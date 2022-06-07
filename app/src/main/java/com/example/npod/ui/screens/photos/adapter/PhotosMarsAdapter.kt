package com.example.npod.ui.screens.photos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.npod.R
import com.example.npod.domain.models.PhotoMars

class PhotosMarsAdapter : RecyclerView.Adapter<PhotosMarsAdapter.PhotosMarsViewHolder>() {
    private val photosList = mutableListOf<PhotoMars>()

    fun setPhotos(photos: List<PhotoMars>) {
        photosList.clear()
        photosList.addAll(photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosMarsViewHolder {
        return PhotosMarsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_mars_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotosMarsViewHolder, position: Int) {
        holder.bind(photosList[position])
    }

    override fun getItemCount(): Int = photosList.size

    class PhotosMarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoItem: PhotoMars) {
            itemView.findViewById<ImageView>(R.id.iv_mars_photo).load(photoItem.img)
            itemView.findViewById<TextView>(R.id.tv_photo_date).text = photoItem.date
        }
    }
}