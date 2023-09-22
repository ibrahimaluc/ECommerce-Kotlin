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
    }

    private fun hideEmptyListView() {
        val emptyListView = binding.cL.findViewById<View>(R.id.emptyListView)
        binding.cL.removeView(emptyListView)
        binding.rvCartList.visibility = View.VISIBLE
    }

    private fun showCartProducts() {
        cartAdapter = CartAdapter(cartList)
        println(cartList)
        binding.rvCartList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartList.adapter = cartAdapter
    }

}