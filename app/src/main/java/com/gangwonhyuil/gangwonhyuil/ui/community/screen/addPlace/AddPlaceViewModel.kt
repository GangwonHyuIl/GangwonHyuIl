package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost.AddPostItem
import com.gangwonhyuil.gangwonhyuil.util.FileUtil
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
        private val fileUtil: FileUtil,
    ) : BaseViewModel() {
        private val _placeListId = MutableStateFlow("")

        private val _placeCategory = MutableStateFlow(PlaceCategory.SHARED_OFFICE)
        private val _placeName = MutableStateFlow("")
        val placeName = _placeName.asStateFlow()
        private val _placeAddress = MutableStateFlow("")
        val placeAddress = _placeAddress.asStateFlow()

        private val _placeImages = MutableStateFlow<List<String>>(emptyList()) // image url list
        private val _imageItems = MutableStateFlow<List<ImageItem>>(emptyList()) // image item list
        val imageItems = _imageItems.asStateFlow()

        private val _postContent = MutableStateFlow("")

        private val _addPlaceState = MutableStateFlow<AddPlaceState>(AddPlaceState.AbleToAdd)
        val addPlaceState = _addPlaceState.asStateFlow()

        init {
            savedStateHandle.get<String>(EXTRA_PLACE_LIST_ID)?.let { placeListId ->
                _placeListId.update { placeListId }
            }

            viewModelScopeEH.launch {
                exceptions.collect { e ->
                    Timber.e(e.message)
                }
            }

            viewModelScopeEH.launch {
                combine(_placeName, _placeAddress) { placeName, placeAddress ->
                    placeName.isNotEmpty() && placeAddress.isNotEmpty()
                }.collect { ableToAdd ->
                    _addPlaceState.update {
                        if (ableToAdd) {
                            AddPlaceState.AbleToAdd
                        } else {
                            AddPlaceState.EmptyPlace
                        }
                    }
                }
            }

            viewModelScopeEH.launch {
                _placeImages.collect { placeImages ->
                    _imageItems.update {
                        mutableListOf<ImageItem>().apply {
                            add(ImageItem.AddImage)
                            addAll(placeImages.map { ImageItem.Image(it) })
                        }
                    }
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

        fun onImageUrisInput(imageUris: List<Uri>) {
            Timber.d("imageUris: $imageUris")

            viewModelScopeEH.launch {
                imageUris.forEach { uri ->
                    viewModelScopeEH.launch {
                        // convert uri to file
                        val imageFile = fileUtil.uriToFile(uri)
                        // TODO: upload file to server & get url
                        // TODO: update _placeImages
                    }
                }
            }
        }

        fun deleteImage(imageUrl: String) {
            _placeImages.update { it - imageUrl }
        }

        fun onPostContentInput(postContent: String) {
            _postContent.update { postContent }
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

sealed interface AddPlaceState {
    val message: String

    data object AbleToAdd : AddPlaceState {
        override val message = ""
    }

    data object EmptyPlace : AddPlaceState {
        override val message = "장소를 추가해 주세요"
    }
}