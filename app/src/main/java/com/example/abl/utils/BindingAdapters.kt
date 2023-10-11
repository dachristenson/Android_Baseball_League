package com.example.abl.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resourceId: Int) {
    imageView.setImageResource(resourceId)
}
