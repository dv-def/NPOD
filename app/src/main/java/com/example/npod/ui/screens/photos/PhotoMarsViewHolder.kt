package com.example.npod.ui.screens.photos

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
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
import com.example.npod.domain.nasa.PhotoMars
import com.google.android.material.card.MaterialCardView

class PhotoMarsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_mars_photo, parent, false)
) {
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
        val date = photoItem.date
        val spannable = SpannableString(date).apply {
            setSpan(
                ForegroundColorSpan(itemView.context.getColor(R.color.mars_primary_color)),
                0,
                date.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        itemView.findViewById<TextView>(R.id.tv_photo_date).text = spannable
    }
}