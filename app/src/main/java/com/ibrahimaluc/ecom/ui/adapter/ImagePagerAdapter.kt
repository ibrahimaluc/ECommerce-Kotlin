package com.ibrahimaluc.ecom.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimaluc.ecom.BuildConfig
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetailAll
import com.ibrahimaluc.ecom.databinding.ItemImageCarouselBinding

class ImagePagerAdapter(
    private val context: Context,
    private var product: ProductDetailAll,
    private val onLikeControl: (ProductDetailAll) -> Unit,
) : RecyclerView.Adapter<ImagePagerAdapter.ImagePagerViewHolder>() {

    class ImagePagerViewHolder(var binding: ItemImageCarouselBinding) : ViewHolder(binding.root)

    var favoriteProductList: List<FavoriteEntity> = emptyList()


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

        val isFavorite = favoriteProductList.any { it.id == (product.id ) }
        println(product.id)
        println(favoriteProductList)
        if (isFavorite) {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_filled)
        } else {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_passive)
        }
        holder.binding.ibLike.setOnClickListener {
            onLikeControl(product)
            updateLikeButton(holder.binding, !isFavorite)
        }
        image(position, holder)


    }

    override fun getItemCount(): Int {
        return product.images.size
    }

    private fun image(position: Int, holder: ImagePagerViewHolder) {
        val imageUrl = product.images[position]
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.product_placeholder_gray)
        Glide.with(context)
            .load(BuildConfig.BASE_URL_MEDIA + imageUrl)
            .apply(requestOptions)
            .into(holder.binding.ivProductImage)
    }

    fun updateFavoriteList(favoriteList: List<FavoriteEntity>) {
        favoriteProductList = favoriteList
        notifyDataSetChanged()
    }

    private fun updateLikeButton(binding: ItemImageCarouselBinding, isFavorite: Boolean) {
        if (isFavorite) {
            binding.ibLike.setImageResource(R.drawable.icon_favorite_filled)
        } else {
            binding.ibLike.setImageResource(R.drawable.icon_favorite_passive)
        }
    }
}