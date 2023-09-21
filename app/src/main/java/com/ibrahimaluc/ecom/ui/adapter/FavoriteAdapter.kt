package com.ibrahimaluc.ecom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val favoriteList: ArrayList<FavoriteEntity>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemFavoriteBinding>(
                inflater,
                R.layout.item_favorite,
                parent,
                false
            )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binding.data = favoriteList[position]
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}