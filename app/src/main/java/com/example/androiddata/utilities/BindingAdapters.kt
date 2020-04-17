package com.example.androiddata.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.NumberFormat

//Complete binding adapter for an image. This can be called from anywhere in the application
//and is identified by the string 'imageUrl'
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context).load(imageUrl).into(view)
}

@BindingAdapter("price")
fun itemPrice (view: TextView, value: Double) {
    val formatter = NumberFormat.getCurrencyInstance()
    val text = "${formatter.format(value)} / each"
    view.text = text
}

