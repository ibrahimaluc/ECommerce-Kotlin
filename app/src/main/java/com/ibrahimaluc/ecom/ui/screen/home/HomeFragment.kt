package com.ibrahimaluc.ecom.ui.screen.home


import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.databinding.FragmentHomeBinding
import com.ibrahimaluc.ecom.data.remote.model.productHome.Product
import com.ibrahimaluc.ecom.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java,
    FragmentHomeBinding::inflate
) {


    private var productList: ArrayList<Product> = arrayListOf()
    private var homeAdapter: HomeAdapter? = null

    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleHomeViewState)
        homeAdapter()
        navigateToSearch()

    }

    private fun handleHomeViewState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)
        uiState.productList?.let {
            productList.clear()
            homeAdapter?.productList = it
        }
    }


    private fun homeAdapter() = with(binding) {
        homeAdapter = HomeAdapter(::navigateToDetail, ::like)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = homeAdapter

    }

    private fun like(position: Int) {
        val product = productList[position]
        if (viewModel.state.value.isFavProduct) {
            val favoriteEntity = FavoriteEntity(
                id = product.id,
                name = product.name,
                price = product.price,
                images = product.images
            )
            viewModel.addFavoriteProductRoom(favoriteEntity)
        } else {
            viewModel.deleteFavWallpaperRoom()

        }
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


