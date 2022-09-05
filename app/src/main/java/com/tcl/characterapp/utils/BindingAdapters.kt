package com.tcl.characterapp.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tcl.characterapp.R

@BindingAdapter("imageUrl")
fun downloadImage(imageView: ImageView, url: String?) {

    url?.let {
        imageView.load(url) {
            crossfade(true)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.animate_loading)
        }

    }
}


// We determine the color according to the status of the characters.
@BindingAdapter("statusColor")
fun changeColor(card: CardView, status: String) {

    if (status == "Dead") {
        card.setCardBackgroundColor(Color.RED)
    } else if (status == "Alive") {
        card.setCardBackgroundColor(Color.GREEN)
    } else {
        card.setCardBackgroundColor(Color.GRAY)
    }
}

@BindingAdapter("statusColor")
fun changeColor(textView: TextView, status: String) {

    if (status == "Dead") {
        textView.setTextColor(Color.RED)
    } else if (status == "Alive") {
        textView.setTextColor(Color.GREEN)
    } else {
        textView.setTextColor(Color.GRAY)
    }
}


