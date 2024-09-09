package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace

import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.SearchPlaceViaKeywordUseCase
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel
    @Inject
    constructor(
        private val searchPlaceViaKeyword: SearchPlaceViaKeywordUseCase,
    ) : BaseViewModel() {
        private val _searchResultItems = MutableStateFlow<List<SearchResultItem>>(emptyList())
        val searchResultItems = _searchResultItems.asStateFlow()

        private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
        val searchState = _searchState.asStateFlow()

        private var searchKeyword = ""
        private var searchPage = 1
        private var canSearchMore = false

        init {
            viewModelScopeEH.launch {
                exceptions.collect {
                    Timber.e(it)
                }
            }
        }

        fun search(keyword: String) {
            searchKeyword = keyword
            searchPage = 1
            viewModelScopeEH.launch {
                val (result, isEnd) = searchPlaceViaKeyword(searchKeyword, searchPage)
                if (result.isEmpty()) {
                    _searchState.update { SearchState.NoResult }
                } else {
                    _searchState.update { SearchState.Idle }
                }

                canSearchMore = !isEnd
                _searchResultItems.update { result }
            }
        }

        fun searchMore() {
            if (!canSearchMore) return

            viewModelScopeEH.launch {
                val (moreResultItems, isEnd) = searchPlaceViaKeyword(searchKeyword, ++searchPage)
                canSearchMore = !isEnd
                _searchResultItems.update { it + moreResultItems }
            }
        }
    }

interface SearchState {
    data object Idle : SearchState

    data object NoResult : SearchState
}