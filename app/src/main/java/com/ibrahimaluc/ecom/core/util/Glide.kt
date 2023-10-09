package com.ibrahimaluc.ecom.core.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.util.ProductConstant

@BindingAdapter("android:getImage")
fun getImage(view: ImageView, imageUri: String?) {
    val baseUrl: String = ProductConstant.BASE_URL_MEDIA
    val placeholder: CircularProgressDrawable = CircularProgressDrawable(view.context).apply {
        setColorSchemeColors(ContextCompat.getColor(view.context, R.color.green))
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
    val options = RequestOptions()
        .placeholder(placeholder)

    Glide.with(view)
        .setDefaultRequestOptions(options)
        .load(baseUrl + imageUri)
        .centerInside()
        .into(view)
}