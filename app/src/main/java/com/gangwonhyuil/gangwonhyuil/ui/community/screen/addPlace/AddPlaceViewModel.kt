package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost.AddPostItem
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class AddPlaceViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : BaseViewModel() {
        private val _placeListId = MutableStateFlow("")
        private val _placeCategory = MutableStateFlow(PlaceCategory.SHARED_OFFICE)
        private val _placeName = MutableStateFlow("")
        val placeName = _placeName.asStateFlow()
        private val _placeAddress = MutableStateFlow("")
        val placeAddress = _placeAddress.asStateFlow()
        private val _placeImages = MutableStateFlow<List<Uri>>(emptyList())
        val placeImages = _placeImages.asStateFlow()
        private val _postContent = MutableStateFlow("")
        val postContent = _postContent.asStateFlow()

        init {
            savedStateHandle.get<String>(EXTRA_PLACE_LIST_ID)?.let { placeListId ->
                _placeListId.update { placeListId }
            }

            viewModelScopeEH.launch {
                exceptions.collect { e ->
                    Timber.e(e.message)
                }
            }
        }

        fun onSelectPlaceCategory(placeCategory: PlaceCategory) {
            _placeCategory.update { placeCategory }
        }

        fun onPlaceNameInput(placeName: String) {
            _placeName.update { placeName }
        }

        fun onPlaceAddressInput(placeAddress: String) {
            _placeAddress.update { placeAddress }
        }

        fun createPlace(): AddPostItem.Place =
            AddPostItem.Place(
                placeListId = _placeListId.value,
                category = _placeCategory.value,
                name = _placeName.value,
                address = _placeAddress.value,
                images = _placeImages.value,
                content = _postContent.value
            )
    }