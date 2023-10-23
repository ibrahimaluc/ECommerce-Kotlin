package com.ibrahimaluc.ecom.core.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:shortenText")
fun TextView.shortenText(text: String?) {
    text?.let {
        val maxLength = 25
        val shortenedText = if (it.length > maxLength) {
            it.substring(0, maxLength) + "..."
        } else {
            it
        }
        setText(shortenedText)
    }
}