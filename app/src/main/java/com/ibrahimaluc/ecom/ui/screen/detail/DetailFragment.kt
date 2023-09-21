package com.ibrahimaluc.ecom.ui.screen.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.databinding.FragmentDetailBinding
import com.ibrahimaluc.ecom.ui.adapter.ImagePagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(
    DetailViewModel::class.java,
    FragmentDetailBinding::inflate
) {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImagePagerAdapter


    override fun onCreateViewInvoke() {
        collectLatestLifecycleFlow(viewModel.state, ::handleDetailViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productId = args.id.toString()
        viewModel.getAllDetail(productId)
        viewPager = binding.viewPager


    }

    private fun handleDetailViewState(uiState: DetailUiState) {
        setProgressStatus(uiState.isLoading)
        with(binding) {
            data = uiState.productDetail
            imageAdapter(data?.productDetail?.images)
        }
    }

    private fun imageAdapter(images: List<String>?) {
        images?.let {
            println("Resim URL'leri: $it")
            adapter = ImagePagerAdapter(requireContext(), it)
            viewPager.adapter = adapter
        }
    }

}