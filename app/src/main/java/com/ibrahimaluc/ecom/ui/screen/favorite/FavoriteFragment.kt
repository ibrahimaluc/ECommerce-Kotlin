package com.ibrahimaluc.ecom.ui.screen.favorite

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteDatabase
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.FragmentFavoriteBinding
import com.ibrahimaluc.ecom.ui.adapter.FavoriteAdapter
import com.ibrahimaluc.ecom.ui.screen.home.HomeFragment
import com.ibrahimaluc.ecom.ui.screen.home.HomeFragmentDirections
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private var favoriteAdapter: FavoriteAdapter? = null
    private var favoriteList: ArrayList<FavoriteEntity> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        setupToolbar()
        loadFavoriteProducts()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun loadFavoriteProducts() {
        val favoriteDao = FavoriteDatabase.getInstance(requireContext()).favoriteDao()
        lifecycleScope.launch {
            favoriteList = favoriteDao.getFavoriteProducts() as ArrayList<FavoriteEntity>
            if (favoriteList.isEmpty()) {
                showEmptyListView()
            } else {
                hideEmptyListView()
                showFavoriteProducts()
            }
        }
    }

    private fun showEmptyListView() {
        binding.recyclerView.visibility = View.GONE
        binding.itemFavoriteEmpty.visibility = View.VISIBLE
        val btnGoHome = view?.findViewById<AppCompatButton>(R.id.btn_go_home)
        btnGoHome?.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }


    private fun hideEmptyListView() {
        binding.itemFavoriteEmpty.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showFavoriteProducts() {
        favoriteAdapter = FavoriteAdapter(favoriteList)
        binding.recyclerView.adapter = favoriteAdapter
    }

}