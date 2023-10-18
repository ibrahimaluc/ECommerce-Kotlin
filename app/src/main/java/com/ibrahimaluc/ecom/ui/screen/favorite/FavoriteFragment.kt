package com.ibrahimaluc.ecom.ui.screen.favorite


import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.FragmentFavoriteBinding
import com.ibrahimaluc.ecom.ui.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FragmentFavoriteBinding>(
    FavoriteViewModel::class.java,
    FragmentFavoriteBinding::inflate
) {
    private var favoriteAdapter: FavoriteAdapter? = null
    private var favoriteList: MutableList<FavoriteEntity> = mutableListOf()


    override fun onCreateViewInvoke() {
        setupToolbar()
        loadFavoriteProducts()
    }


    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadFavoriteProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            val favorites = viewModel.checkProducts()
            favoriteList = favorites as MutableList<FavoriteEntity>
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
        favoriteAdapter = FavoriteAdapter(favoriteList, ::deleteProduct)
        binding.recyclerView.adapter = favoriteAdapter
    }

    private fun deleteProduct(position: Int) {
        lifecycleScope.launch {
            if (position >= 0 && position < favoriteList.size) {
                val deletedFavoriteEntity = favoriteList[position]
                viewModel.deleteFavWallpaperRoom(deletedFavoriteEntity)
                favoriteList.removeAt(position)
                favoriteAdapter?.notifyItemRemoved(position)
                if (favoriteList.isEmpty()) {
                    showEmptyListView()
                } else {
                    favoriteAdapter?.notifyItemRangeChanged(position, favoriteList.size)
                }
            }
        }
    }
}