package com.ibrahimaluc.ecom.ui.screen.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.databinding.FragmentHomeBinding
import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult
import com.ibrahimaluc.ecom.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.ArrayList


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java,
    FragmentHomeBinding::inflate
), SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var searchViewModel: SearchViewModel
    private var searchList: ArrayList<SearchResult> = arrayListOf()
    private var searchAdapter: SearchAdapter? = null
    override fun onCreateViewInvoke() {

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        collectLatestLifecycleFlow(viewModel.state,::handleHomeViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllProducts()
    }


    private fun handleHomeViewState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)
        uiState.productList.let {
            it?.clear()
        }
    }
    private  fun handleSearchViewState(uiState: SearchUiState){
        setProgressStatus(uiState.isLoading)
        uiState.searchList.let {
            searchList.clear()
            if (it != null) {
                searchAdapter?.searchList = it
            }
        }

    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        if (!searchQuery.isNullOrBlank()) {
            searchViewModel.getSearchResults(searchQuery)


        }
        return true
    }

    override fun onQueryTextChange(searchQuery: String?): Boolean {
        if (!searchQuery.isNullOrBlank()) {
            searchViewModel.getSearchResults(searchQuery)
        }
        return true

    }

}