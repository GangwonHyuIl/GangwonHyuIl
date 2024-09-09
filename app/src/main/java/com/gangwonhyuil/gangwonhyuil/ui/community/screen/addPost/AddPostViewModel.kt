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

        private var deletedPlaceList: AddPostItem.PlaceList? = null
        private var deletedPlaces: List<AddPostItem.Place> = emptyList()

        private val _addPostItems = MutableStateFlow<List<AddPostItem>>(emptyList())
        val addPostItems = _addPostItems.asStateFlow()

        private var _registerState: RegisterState = RegisterState.EmptyContent
        val registerState: RegisterState get() = _registerState

        init {
            viewModelScopeEH.launch {
                exceptions.collect {
                    Timber.e(it)
                }
            }

            combine(_content, _placeLists, _places) { content, placeLists, places ->
                // set post items for recycler view
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

                // set register state
                _registerState =
                    if (content.content.isEmpty()) {
                        RegisterState.EmptyContent
                    } else if (placeLists.any { it.name.isEmpty() }) {
                        RegisterState.EmptyPlaceListName
                    } else {
                        var isAnyPlaceListEmpty = false
                        for (placeList in placeLists) {
                            if (places.none { it.placeListId == placeList.id }) {
                                isAnyPlaceListEmpty = true
                                break
                            }
                        }
                        if (isAnyPlaceListEmpty) RegisterState.EmptyPlaceList else RegisterState.AbleToRegister
                    }
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
            deletedPlaceList = _placeLists.value.find { it.id == placeListId }
            deletedPlaces = _places.value.filter { it.placeListId == placeListId }

            _placeLists.update { placeLists ->
                placeLists - deletedPlaceList!!
            }
            _places.update { places ->
                places - deletedPlaces.toSet()
            }
        }

        fun undonDeletePlaceList(placeListId: String) {
            _placeLists.update { placeLists ->
                placeLists + deletedPlaceList!!
            }
            _places.update { places ->
                places + deletedPlaces
            }
            // reset deleted place list & places
            deletedPlaceList = null
            deletedPlaces = emptyList()
        }

        fun onAddPlace(place: AddPostItem.Place) {
            _places.update { places ->
                places + place
            }
        }

        fun onDeletePlace(placeId: String) {
            deletedPlaces = _places.value.filter { it.id == placeId }
            _places.update { places ->
                places - deletedPlaces.toSet()
            }
        }

        fun undonDeletePlace(placeId: String) {
            _places.update { places ->
                places + deletedPlaces
            }
            // reset deleted places
            deletedPlaces = emptyList()
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

sealed interface RegisterState {
    val message: String

    data object AbleToRegister : RegisterState {
        override val message = ""
    }

    data object EmptyContent : RegisterState {
        override val message = "게시글 내용을 입력해주세요"
    }

    data object EmptyPlaceListName : RegisterState {
        override val message = "장소 목록 이름을 입력해주세요"
    }

    data object EmptyPlaceList : RegisterState {
        override val message = "장소 목록에 장소를 추가해주세요"
    }
}