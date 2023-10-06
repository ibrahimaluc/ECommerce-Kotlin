package com.ibrahimaluc.ecom.ui.screen.detail

import android.os.Bundle
import android.view.View
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
import com.ibrahimaluc.ecom.data.local.cart.CartDatabase
import com.ibrahimaluc.ecom.data.local.cart.CartEntity
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteDatabase
import com.ibrahimaluc.ecom.data.local.favorite.FavoriteEntity
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
    private val favoriteStatusList = mutableListOf<Boolean>()


    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleDetailViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productId = args.id.toString()
        viewModel.getAllDetail(productId)
        sizeListener()
        navigateToSearch()

    }

    private fun handleDetailViewState(uiState: DetailUiState) {
        setProgressStatus(uiState.isLoading)
        with(binding) {
            data = uiState.productDetail
            imageAdapter(data?.productDetail?.images)
            favoriteStatusList.clear()
            data?.productDetail?.images?.let { List(it.size) { false } }
                ?.let { favoriteStatusList.addAll(it) }
            addBasket()
        }
    }

    private fun imageAdapter(images: List<String>?) {
        images?.let {
            adapter = ImagePagerAdapter(requireContext(), it, ::addLike, favoriteStatusList)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.indicator.setViewPager(binding.viewPager)
            val heartBeatAnimation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.indicator)
            binding.indicator.startAnimation(heartBeatAnimation)

        }
    }

    private fun navigateToSearch() {
        binding.searchButton.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToSearchFragment()
            findNavController().navigate(action)
        }

    }

    private fun sizeListener() {
        binding.btnSizeS.setOnClickListener {
            selectSizeButton(binding.btnSizeS)
        }

        binding.btnSizeM.setOnClickListener {
            selectSizeButton(binding.btnSizeM)
        }

        binding.btnSizeL.setOnClickListener {
            selectSizeButton(binding.btnSizeL)
        }

        binding.btnSizeXl.setOnClickListener {
            selectSizeButton(binding.btnSizeXl)
        }
    }

    private fun selectSizeButton(selectedButton: AppCompatButton) {
        binding.btnSizeS.isSelected = false
        binding.btnSizeM.isSelected = false
        binding.btnSizeL.isSelected = false
        binding.btnSizeXl.isSelected = false
        selectedButton.isSelected = true
        size = selectedButton.text.toString()
    }


    private fun addLike(position: Int) {
        val product = binding.data?.productDetail
        val favoriteEntity = FavoriteEntity(
            id = product?.id,
            name = product?.name,
            price = product?.price,
            images = product?.images?.get(0),
        )

        val favoriteDao = FavoriteDatabase.getInstance(requireContext()).favoriteDao()
        val isLiked = favoriteStatusList[position]
        lifecycleScope.launch {
            if (isLiked) {
                favoriteDao.delete(favoriteEntity)
                favoriteStatusList[position] = false
                Toast.makeText(
                    requireContext(),
                    "Deleted from your favorites.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                favoriteDao.insert(favoriteEntity)
                favoriteStatusList[position] = true
                Toast.makeText(
                    requireContext(),
                    "Added to your favorites.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            adapter.notifyDataSetChanged()

        }
    }

    private fun addBasket() {
        val product = binding.data?.productDetail
        binding.btAddToCart.setOnClickListener {
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
                        )
                            .show()
                    }
                }
            }
        }
    }
}

