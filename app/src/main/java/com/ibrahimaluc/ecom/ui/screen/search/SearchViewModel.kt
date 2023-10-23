package com.ibrahimaluc.ecom.ui.screen.search

import androidx.lifecycle.viewModelScope
import com.ibrahimaluc.ecom.core.base.BaseViewModel
import com.ibrahimaluc.ecom.core.util.Resource
import com.ibrahimaluc.ecom.data.remote.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {
    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState(isLoading = false))
    val state: StateFlow<SearchUiState> get() = _state

    fun getSearchResults(searchQuery: String) {
        job = viewModelScope.launch {
            job = viewModelScope.launch {
                productRepository.getSearchResults(searchQuery).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = SearchUiState(
                                searchList = result.data?.searchResult,
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



