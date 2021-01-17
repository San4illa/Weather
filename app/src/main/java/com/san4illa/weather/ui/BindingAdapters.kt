package com.san4illa.weather.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.bindImage(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}