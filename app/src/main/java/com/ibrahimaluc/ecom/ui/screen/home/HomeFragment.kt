package com.ibrahimaluc.ecom.ui.screen.home


import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.core.extensions.showToast
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteProductsDAO
import com.ibrahimaluc.ecom.databinding.FragmentHomeBinding
import com.ibrahimaluc.ecom.data.remote.model.productHome.Product
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepository
import com.ibrahimaluc.ecom.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.ArrayList


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java,
    FragmentHomeBinding::inflate
) {


    private var productList: ArrayList<Product> = arrayListOf()
    private var homeAdapter: HomeAdapter? = null
    private var favoriteProductList: List<FavoriteEntity> = emptyList()


    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleHomeViewState)
        homeAdapter()
        navigateToSearch()
        checkFavorites()

    }

    private fun handleHomeViewState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)
        uiState.productList?.let {
            productList.clear()
            homeAdapter?.productList = it
        }
    }

    private fun checkFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            val favorites = viewModel.fetchFavoriteProducts()
            favoriteProductList = favorites
            homeAdapter?.favoriteProductList = favoriteProductList
            homeAdapter?.updateFavoriteList(favoriteProductList)
        }
    }


    private fun homeAdapter() = with(binding) {
        homeAdapter = HomeAdapter(::navigateToDetail, ::like)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = homeAdapter

    }

    private fun like(product: Product) {

        val favoriteEntity = FavoriteEntity(
            id = product.id,
            name = product.name,
            price = product.price,
            images = product.images
        )
        val isFavorite = favoriteProductList.any { it.id == product.id }

        if (!isFavorite) {
            viewModel.addFavoriteProductRoom(favoriteEntity)
            context?.showToast("Added to favorites.")
        } else {
            viewModel.deleteFavWallpaperRoom(favoriteEntity)
            context?.showToast("Removed from favorites.")
        }
        checkFavorites()
    }


    private fun navigateToSearch() {
        binding.searchButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }

    }

    private fun navigateToDetail(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }
}


