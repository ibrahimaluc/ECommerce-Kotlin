package com.ibrahimaluc.ecom.ui.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.local.FavoriteDatabase
import com.ibrahimaluc.ecom.data.local.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.FragmentFavoriteBinding
import com.ibrahimaluc.ecom.ui.adapter.FavoriteAdapter
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
        val emptyListView = LayoutInflater.from(requireContext()).inflate(
            R.layout.item_empty_favorite, binding.cL, false
        )
        binding.cL.addView(emptyListView)
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideEmptyListView() {
        val emptyListView = binding.cL.findViewById<View>(R.id.emptyListView)
        binding.cL.removeView(emptyListView)
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showFavoriteProducts() {
        favoriteAdapter = FavoriteAdapter(favoriteList)
        binding.recyclerView.adapter = favoriteAdapter
    }

}