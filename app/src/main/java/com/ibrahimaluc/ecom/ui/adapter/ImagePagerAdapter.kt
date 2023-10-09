package com.ibrahimaluc.ecom.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimaluc.ecom.BuildConfig
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.databinding.ItemImageCarouselBinding

class ImagePagerAdapter(
    private val context: Context,
    private val imageList: List<String>,
    private val onLikeClickListener: (position: Int) -> Unit,
    private val favoriteStatusList: MutableList<Boolean>,


    ) : RecyclerView.Adapter<ImagePagerAdapter.ImagePagerViewHolder>() {

    class ImagePagerViewHolder(var binding: ItemImageCarouselBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePagerViewHolder {
        return ImagePagerViewHolder(
            ItemImageCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagePagerViewHolder, position: Int) {
        image(position, holder)
        like(position, holder)

    }

    private fun like(position: Int, holder: ImagePagerViewHolder) {
        val isLiked = favoriteStatusList[position]
        if (isLiked) {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_filled)
            favoriteStatusList.replaceAll { true }
        } else {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_passive)
            favoriteStatusList.replaceAll { false }
        }

        holder.binding.ibLike.setOnClickListener {
            onLikeClickListener(position)
        }
    }

    private fun image(position: Int, holder: ImagePagerViewHolder) {
        val imageUrl = imageList[position]
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.product_placeholder_gray)
        Glide.with(context)
            .load(BuildConfig.BASE_URL_MEDIA + imageUrl)
            .apply(requestOptions)
            .into(holder.binding.ivProductImage)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}