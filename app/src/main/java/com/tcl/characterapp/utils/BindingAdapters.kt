package com.tcl.characterapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
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


