package com.ibrahimaluc.ecom.ui.screen.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.cart.CartDatabase
import com.ibrahimaluc.ecom.data.local.cart.CartEntity
import com.ibrahimaluc.ecom.databinding.FragmentCartBinding
import com.ibrahimaluc.ecom.ui.adapter.CartAdapter
import kotlinx.coroutines.launch

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private var cartAdapter: CartAdapter? = null
    private var cartList: ArrayList<CartEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        setupToolbar()
        loadCartProducts()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun loadCartProducts() {
        val cartDao = CartDatabase.getInstance(requireContext()).cartDao()
        lifecycleScope.launch {
            cartList = cartDao.getCartProducts() as ArrayList<CartEntity>
            if (cartList.isEmpty()) {
                showEmptyListView()
            } else {
                hideEmptyListView()
                showCartProducts()
            }
        }
    }

    private fun showEmptyListView() {
        val emptyListView = LayoutInflater.from(requireContext()).inflate(
            R.layout.item_empty_cart, binding.cL, false
        )
        binding.cL.addView(emptyListView)
        binding.rvCartList.visibility = View.GONE
        binding.llCart.visibility = View.GONE
        binding.btCart.visibility = View.GONE
    }

    private fun hideEmptyListView() {
        val emptyListView = binding.cL.findViewById<View>(R.id.emptyListView)
        binding.cL.removeView(emptyListView)
        binding.rvCartList.visibility = View.VISIBLE
        binding.llCart.visibility = View.VISIBLE
        binding.btCart.visibility = View.VISIBLE

    }

    private fun showCartProducts() {
        cartAdapter = CartAdapter(cartList, ::deleteToCart, ::minus, ::plus)
        binding.rvCartList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartList.adapter = cartAdapter
        calculateCost()
    }

    private fun deleteToCart(position: Int) {
        val deletedCartEntity = cartList[position]
        val cartDao = CartDatabase.getInstance(requireContext()).cartDao()
        lifecycleScope.launch {
            cartDao.delete(deletedCartEntity)
            cartList.removeAt(position)
            cartAdapter?.notifyItemRemoved(position)

            if (cartList.isEmpty()) {
                showEmptyListView()
            }
            calculateCost()
        }

    }

    private fun plusOrMinus(position: Int, change: Int) {
        val cartItem = cartList[position]
        val newQuantity = cartItem.quantity + change

        if (newQuantity >= 1) {
            cartItem.quantity = newQuantity
            updateCartItem(position, cartItem)
        }
    }

    private fun updateCartItem(position: Int, cartItem: CartEntity) {
        cartAdapter?.notifyItemChanged(position)
        val viewHolder = binding.rvCartList.findViewHolderForAdapterPosition(position)
        if (viewHolder is CartAdapter.CartViewHolder) {
            viewHolder.binding.quantity.text = cartItem.quantity.toString()
        }
        calculateCost()
    }

    private fun plus(position: Int) {
        plusOrMinus(position, 1)
    }

    private fun minus(position: Int) {
        plusOrMinus(position, -1)
    }

    private fun calculateCost() {
        var totalPrice = 0.0
        for (cartItem in cartList) {
            totalPrice += (cartItem.price ?: 0.0) * cartItem.quantity
        }
        val shippingCost = if (totalPrice >= 200.0) {
            0.0
        } else {
            39.9
        }
        val tax = totalPrice * 0.20
        val grandTotal = totalPrice + shippingCost + tax

        binding.tvItemsValue.text = String.format("₺ %.2f", totalPrice)
        binding.tvShippingValue.text = String.format("₺ %.2f", shippingCost)
        binding.tvTaxValue.text = String.format("₺ %.2f", tax)
        binding.tvTotalValue.text = String.format("₺ %.2f", grandTotal)
    }
}