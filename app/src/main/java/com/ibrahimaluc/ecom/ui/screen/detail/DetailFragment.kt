package com.ibrahimaluc.ecom.ui.screen.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
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
    private var size: String = ""


    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleDetailViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productId = args.id.toString()
        viewModel.getAllDetail(productId)

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

    private fun handleDetailViewState(uiState: DetailUiState) {
        setProgressStatus(uiState.isLoading)
        with(binding) {
            data = uiState.productDetail
            imageAdapter(data?.productDetail?.images)
            addBasket()
        }
    }

    private fun imageAdapter(images: List<String>?) {
        images?.let {
            adapter = ImagePagerAdapter(requireContext(), it)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


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

    private fun addBasket() {
        val product = binding.data?.productDetail
        binding.btAddToCart.setOnClickListener {
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
                    Toast.makeText(requireContext(), "Added to your basket.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}

