package com.ibrahimaluc.ecom.ui.screen.search


import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimaluc.ecom.core.base.BaseFragment
import com.ibrahimaluc.ecom.core.extensions.collectLatestLifecycleFlow
import com.ibrahimaluc.ecom.core.extensions.hideKeyboard
import com.ibrahimaluc.ecom.core.extensions.showKeyboard
import com.ibrahimaluc.ecom.databinding.FragmentSearchBinding
import com.ibrahimaluc.ecom.data.remote.model.productSearch.SearchResult
import com.ibrahimaluc.ecom.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    SearchViewModel::class.java,
    FragmentSearchBinding::inflate
), SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {


    private var searchList: ArrayList<SearchResult> = arrayListOf()
    private var searchAdapter: SearchAdapter? = null


    override fun onCreateViewInvoke() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
        collectLatestLifecycleFlow(viewModel.state, ::handleSearchViewState)
        searchAdapter()
        backButton()

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
            searchList.clear()
            searchAdapter?.searchList = emptyList()
            searchAdapter?.notifyDataSetChanged()
            binding.searchControl = false
            hideKeyboard()
        }
        return true
    }


    private fun handleSearchViewState(uiState: SearchUiState) {
        setProgressStatus(uiState.isLoading)
        uiState.searchList?.let {
            searchList.clear()
            searchAdapter?.searchList = it
        }
    }

    private fun searchAdapter() = with(binding) {
        searchAdapter = SearchAdapter(::navigateToDetail)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerView.adapter = searchAdapter

    }

    private fun navigateToDetail(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }


    private fun backButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


}


