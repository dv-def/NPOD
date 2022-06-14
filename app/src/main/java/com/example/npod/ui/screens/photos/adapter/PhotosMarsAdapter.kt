package com.example.npod.ui.screens.photos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.example.npod.R
import com.example.npod.domain.models.PhotoMars
import com.google.android.material.card.MaterialCardView

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
        private var isFullScreen = false

        fun bind(photoItem: PhotoMars) {
            val image = itemView.findViewById<ImageView>(R.id.iv_mars_photo)
                .apply {
                    load(photoItem.img)
                }

            image.setOnClickListener {
                isFullScreen = isFullScreen.not()

                val cardView: MaterialCardView = itemView.findViewById(R.id.item_mars_photo_card_view)

                TransitionManager.beginDelayedTransition(
                    cardView,
                    TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )

                val cardParams = cardView.layoutParams
                cardParams.height = if (isFullScreen) {
                    ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                }

                cardView.layoutParams = cardParams

                val imageParams = image.layoutParams
                imageParams.height = if (isFullScreen) {
                    ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                }

                image.layoutParams = imageParams
                image.scaleType = if (isFullScreen) {
                    ImageView.ScaleType.CENTER_CROP
                } else {
                    ImageView.ScaleType.FIT_CENTER
                }
            }
            itemView.findViewById<TextView>(R.id.tv_photo_date).text = photoItem.date
        }
    }
}