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

        init {
            viewModelScopeEH.launch {
                exceptions.collect {
                    Timber.e(it)
                }
            }
        }

        fun search(keyword: String) {
            viewModelScopeEH.launch {
                _searchResultItems.update {
                    searchPlaceViaKeyword(keyword)
                }
            }
        }
    }