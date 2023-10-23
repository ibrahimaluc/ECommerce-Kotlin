package com.ibrahimaluc.ecom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.cart.CartEntity
import com.ibrahimaluc.ecom.databinding.ItemCartBinding

class CartAdapter(
    private val cartList: ArrayList<CartEntity>,
    private val onTrashClickListener: (position: Int) -> Unit,
    private val onMinusClickListener: (position: Int) -> Unit,
    private val onPlusClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCartBinding>(
                inflater,
                R.layout.item_cart,
                parent,
                false
            )
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.data = cartList[position]
        holder.binding.ibTrash.setOnClickListener {
            onTrashClickListener(position)
        }
        holder.binding.ibPlus.setOnClickListener {
            onPlusClickListener(position)
        }
        holder.binding.ibMinus.setOnClickListener {
            onMinusClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}