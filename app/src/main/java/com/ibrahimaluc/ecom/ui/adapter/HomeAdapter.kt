package com.ibrahimaluc.ecom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ibrahimaluc.ecom.databinding.ItemHomeBinding
import com.ibrahimaluc.ecom.data.remote.model.productHome.Product


class HomeAdapter(
    private val clickControl: (Int) -> Unit,
    private val onLikeControl: (position: Int, Product) -> Unit
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
        holder.binding.ibLike.setOnClickListener {
            onLikeControl(position, product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}