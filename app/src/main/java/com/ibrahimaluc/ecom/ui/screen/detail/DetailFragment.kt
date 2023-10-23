package com.ibrahimaluc.ecom.ui.screen.detail


import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.core.extensions.showToast
import com.ibrahimaluc.ecom.data.local.cart.CartDatabase
import com.ibrahimaluc.ecom.data.local.cart.CartEntity
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
import com.ibrahimaluc.ecom.data.remote.model.productDetail.ProductDetailAll
import com.ibrahimaluc.ecom.databinding.FragmentDetailBinding
import com.ibrahimaluc.ecom.ui.adapter.ImagePagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(
    DetailViewModel::class.java,
    FragmentDetailBinding::inflate
) {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var adapter: ImagePagerAdapter
    private var size: String = ""
    private var favoriteProductList: List<FavoriteEntity> = emptyList()

    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleDetailViewState)
        val productId = args.id.toString()
        viewModel.getAllDetail(productId)
        sizeListener()
        navigateToSearch()
    }

    private fun handleDetailViewState(uiState: DetailUiState) {
        setProgressStatus(uiState.isLoading)
        val product = uiState.productDetail
        with(binding) {
            data = product
            if (product != null) {
                imageAdapter(product)
            }
            addBasket()
        }
    }

    private fun imageAdapter(product: ProductDetailAll) {
        product.images.let {
            adapter = ImagePagerAdapter(requireContext(), product, ::like)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.indicator.setViewPager(binding.viewPager)
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.indicator)
            binding.indicator.startAnimation(anim)
            checkFavorites()
        }
    }

    private fun checkFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            val favorites = viewModel.fetchFavoriteProducts()
            favoriteProductList = favorites
            adapter.favoriteProductList = favoriteProductList
            adapter.updateFavoriteList(favoriteProductList)
        }
    }

    private fun like(product: ProductDetailAll) {
        val favoriteEntity = FavoriteEntity(
            id = product.id,
            name = product.name,
            price = product.price,
            images = product.images?.get(0)
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
        binding.buttonSearch.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    private fun sizeListener() = with(binding) {
        buttonSizeS.setOnClickListener {
            selectSizeButton(buttonSizeS)
        }
        buttonSizeM.setOnClickListener {
            selectSizeButton(buttonSizeM)
        }
        buttonSizeL.setOnClickListener {
            selectSizeButton(buttonSizeL)
        }
        buttonSizeXL.setOnClickListener {
            selectSizeButton(buttonSizeXL)
        }
    }

    private fun selectSizeButton(selectedButton: AppCompatButton) {
        binding.buttonSizeS.isSelected = false
        binding.buttonSizeM.isSelected = false
        binding.buttonSizeL.isSelected = false
        binding.buttonSizeXL.isSelected = false
        selectedButton.isSelected = true
        size = selectedButton.text.toString()
    }

    private fun addBasket() {
        val product = binding.data
        binding.buttonAddToCart.setOnClickListener {
            if (size.isEmpty()) {
                Toast.makeText(requireContext(), "Please select your size.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val cartEntity = CartEntity(
                    id = product?.id ?: 0,
                    name = product?.name,
                    price = product?.price,
                    images = product?.images?.get(0),
                    seller = product?.seller,
                    size = size,
                    quantity = 1
                )
                val cartDao = CartDatabase.getInstance(requireContext()).cartDao()
                lifecycleScope.launch {
                    val existingEntity =
                        cartDao.getCartEntityByIdAndSize(cartEntity.id, cartEntity.size)
                    if (existingEntity != null) {
                        Toast.makeText(
                            requireContext(),
                            "Already in your basket.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        cartDao.insert(cartEntity)
                        Toast.makeText(
                            requireContext(),
                            "Added to your basket.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}