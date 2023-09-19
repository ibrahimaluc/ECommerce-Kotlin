package com.ibrahimaluc.ecom.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ibrahimaluc.ecom.data.local.FavoriteDatabase
import com.ibrahimaluc.ecom.data.local.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.ItemHomeBinding
import com.ibrahimaluc.ecom.domain.model.productHome.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeAdapter(private val clickControl: (Int) -> Unit) :
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
            product.id?.let { productId -> clickControl(productId) }
        }
        holder.binding.ivLikeButton.setOnClickListener {
            addFavorite(holder.itemView.context, product, holder)
        }

    }


    override fun getItemCount(): Int {
        return productList.size
    }

    private fun addFavorite(context: Context, product: Product, holder: HomeViewHolder) {

        val favoriteEntity = FavoriteEntity(
            id = product.id,
            name = product.name,
            price = product.price,
            images = product.images
        )
        val favoriteDao =
            FavoriteDatabase.getInstance(context).favoriteDao()
        CoroutineScope(Dispatchers.IO).launch {
            val existingEntity = favoriteDao.getFavoriteEntityById(favoriteEntity.id)
            if (existingEntity == null) {
                favoriteDao.insert(favoriteEntity)
                holder.itemView.post {
                    Toast.makeText(
                        context,
                        "Favori olarak eklendi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                holder.itemView.post {
                    Toast.makeText(
                        context,
                        "zaten.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}