package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class AddPostViewModel
    @Inject
    constructor() : BaseViewModel() {
        private val _content = MutableStateFlow(AddPostItem.Content(""))
        private val _placeLists = MutableStateFlow<List<AddPostItem.PlaceList>>(emptyList())
        private val _places = MutableStateFlow<List<AddPostItem.Place>>(emptyList())

        private val _addPostItems = MutableStateFlow<List<AddPostItem>>(emptyList())
        val addPostItems = _addPostItems.asStateFlow()

        init {
            viewModelScopeEH.launch {
                exceptions.collect {
                    Timber.e(it)
                }
            }

            combine(_content, _placeLists, _places) { content, placeLists, places ->
                val items =
                    mutableListOf<AddPostItem>().apply {
                        add(content)
                        for (placeList in placeLists) {
                            add(placeList)
                            addAll(places.filter { it.placeListId == placeList.id })
                            add(AddPostItem.AddPlace(placeList.id))
                        }
                        add(AddPostItem.AddPlaceList)
                    }

                _addPostItems.update { items }
            }.launchIn(viewModelScopeEH)
        }

        fun onContentInput(content: String) {
            _content.update { it.copy(content = content) }
        }

        fun onPlaceListNameInput(
            placeListId: String,
            name: String,
        ) {
            _placeLists.update { placeLists ->
                placeLists.map { placeList ->
                    if (placeList.id == placeListId) placeList.copy(name = name) else placeList
                }
            }
        }

        fun onDeletePlaceList(placeListId: String) {
            _placeLists.update { placeLists ->
                placeLists.filter { it.id != placeListId }
            }
            _places.update { places ->
                places.filter { it.placeListId != placeListId }
            }
        }

        fun onAddPlace(place: AddPostItem.Place) {
            _places.update { places ->
                places + place
            }
        }

        fun onDeletePlace(placeId: String) {
            _places.update { places ->
                places.filter { it.id != placeId }
            }
        }

        fun onAddPlaceList() {
            _placeLists.update { placeLists ->
                placeLists + AddPostItem.PlaceList(name = "")
            }
        }

        fun registerPost() {
            // TODO: register post
            Timber.d("registerPost")
        }
    }