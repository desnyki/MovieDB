package com.desnyki.moviedb.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        val fullImageUrl = "https://image.tmdb.org/t/p/w185/$imageUrl"
        Glide.with(view.context)
            .load(fullImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}