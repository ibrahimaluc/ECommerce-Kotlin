package com.ibrahimaluc.ecom.ui.screen.detail

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.data.local.cart.CartDatabase
import com.ibrahimaluc.ecom.data.local.cart.CartEntity
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


    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleDetailViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productId = args.id.toString()
        viewModel.getAllDetail(productId)
    }

    private fun handleDetailViewState(uiState: DetailUiState) {
        setProgressStatus(uiState.isLoading)
        with(binding) {
            data = uiState.productDetail
            imageAdapter(data?.productDetail?.images)
            //addBasket()
        }
    }

    private fun imageAdapter(images: List<String>?) {
        images?.let {
            adapter = ImagePagerAdapter(requireContext(), it)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        }
    }

    /*private fun addBasket() {
        val product = binding.data?.productDetail
        binding.btAddToBasket.setOnClickListener {
            val cartEntity = CartEntity(
                id = product?.id ?: -1,
                name = product?.name ?: "Unknown",
                price = product?.price ?: 0.0,
                images = product?.images?.get(0) ?: "No image",
                seller = product?.seller ?: "Unknown Seller",
                size = "null"
            )
            val cartDao = CartDatabase.getInstance(requireContext()).cartDao()
            lifecycleScope.launch {
                val existingEntity = cartDao.getCartEntityById(cartEntity.id)
                if (existingEntity == null) {
                    cartDao.insert(cartEntity)
                    Toast.makeText(requireContext(), "Added to your basket", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Already in your basket",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }*/
}

