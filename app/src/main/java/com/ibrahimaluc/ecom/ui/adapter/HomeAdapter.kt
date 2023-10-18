package com.ibrahimaluc.ecom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.ItemHomeBinding
import com.ibrahimaluc.ecom.data.remote.model.productHome.Product


class HomeAdapter(
    private val clickControl: (Int) -> Unit,
    private val onLikeControl: (Product) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(var binding: ItemHomeBinding) : ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem

    }
    private val listDiffer = AsyncListDiffer(this, diffCallBack)
    var productList: List<Product>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    var favoriteProductList: List<FavoriteEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.data = product
        holder.binding.productCard.setOnClickListener {
            clickControl(product.id)
        }
        val isFavorite = favoriteProductList.any { it.id == product.id }
        if (isFavorite) {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_filled)
        } else {
            holder.binding.ibLike.setImageResource(R.drawable.icon_favorite_passive)
        }
        holder.binding.ibLike.setOnClickListener {
            onLikeControl(product)
            updateLikeButton(holder.binding, !isFavorite)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateFavoriteList(favoriteList: List<FavoriteEntity>) {
        favoriteProductList = favoriteList
        notifyDataSetChanged()
    }

    private fun updateLikeButton(binding: ItemHomeBinding, isFavorite: Boolean) {
        if (isFavorite) {
            binding.ibLike.setImageResource(R.drawable.icon_favorite_filled)
        } else {
            binding.ibLike.setImageResource(R.drawable.icon_favorite_passive)
        }
    }
}