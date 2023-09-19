package com.ibrahimaluc.ecom.ui.screen.home


import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.core.extensions.hideKeyboard
import com.ibrahimaluc.ecom.databinding.FragmentHomeBinding
import com.ibrahimaluc.ecom.domain.model.productHome.Product
import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult
import com.ibrahimaluc.ecom.ui.adapter.HomeAdapter
import com.ibrahimaluc.ecom.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class.java,
    FragmentHomeBinding::inflate
), SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {


    private var productList: ArrayList<Product> = arrayListOf()
    private var searchList: ArrayList<SearchResult> = arrayListOf()
    private var searchAdapter: SearchAdapter? = null
    private var homeAdapter: HomeAdapter? = null

    override fun onCreateViewInvoke() {

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(this)

        collectLatestLifecycleFlow(viewModel.state, ::handleHomeViewState)
        collectLatestLifecycleFlow(viewModel.searchState, ::handleSearchViewState)

        homeAdapter()
        searchAdapter()
        inItClickListener()

    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        if (!searchQuery.isNullOrBlank()) {
            viewModel.getSearchResults(searchQuery)
            binding.searchControl = true
            hideKeyboard()
        }
        return true
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        if (!newQuery.isNullOrBlank()) {
            viewModel.getSearchResults(newQuery)
            binding.searchControl = true
        } else {

            binding.searchControl = false
            hideKeyboard()
        }
        return true
    }

    private fun handleHomeViewState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)
        uiState.productList?.let {
            productList.clear()
            homeAdapter?.productList = it
        }
    }

    private fun handleSearchViewState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)
        //Log.d("SearchFragment", "Veriler geldi: ${uiState.searchList}")
        uiState.searchList?.let {
            searchList.clear()
            searchAdapter?.searchList = it
        }
    }

    private fun homeAdapter() = with(binding) {
        homeAdapter = HomeAdapter(::navigateToDetail)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = homeAdapter

    }

    private fun searchAdapter() = with(binding) {
        searchAdapter = SearchAdapter(::navigateToDetail)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = searchAdapter

    }

    private fun navigateToDetail(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun inItClickListener() = with(binding) {

        searchView.setOnCloseListener {
            hideKeyboard()
            searchView.setQuery("", false)
            false
        }
    }

}


