package com.ibrahimaluc.ecom.ui.screen.search

import androidx.lifecycle.viewModelScope
import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.domain.model.productSearch.SearchResult
import com.ibrahimaluc.ecom.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.ArrayList

@HiltViewModel
class SearchViewModel(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState(isLoading = false))
    val state: StateFlow<SearchUiState> get() = _state

    fun getSearchResults() {
        job = viewModelScope.launch {
            job = viewModelScope.launch {
                productRepository.getSearchResults().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = SearchUiState(
                                searchList = result.data?.searchResult as ArrayList<SearchResult>?,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _state.value = SearchUiState(
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = SearchUiState(
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
            }
        }
    }
}