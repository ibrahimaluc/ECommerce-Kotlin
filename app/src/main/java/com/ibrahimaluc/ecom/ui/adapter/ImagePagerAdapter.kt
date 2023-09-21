package com.ibrahimaluc.ecom.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.util.ProductConstant
import com.ibrahimaluc.ecom.databinding.ItemImageCarouselBinding

class ImagePagerAdapter(
    private val context: Context,
    private val imageList: List<String>
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
        val imageUrl = imageList[position]
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        Glide.with(context)
            .load(ProductConstant.BASE_URL_MEDIA + imageUrl)
            .apply(requestOptions)
            .into(holder.binding.ivItemCarousel)


    }


    override fun getItemCount(): Int {
        return imageList.size
    }
}