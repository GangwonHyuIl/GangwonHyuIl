package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import com.gangwonhyuil.gangwonhyuil.ui.community.entity.AddPost
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.AddPostUseCase
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.GetUserIdUseCase
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
    constructor(
        private val getUserId: GetUserIdUseCase,
        private val addPost: AddPostUseCase,
    ) : BaseViewModel() {
        private val _content = MutableStateFlow(AddPostItem.Content(""))
        private val _placeLists = MutableStateFlow<List<AddPostItem.PlaceList>>(emptyList())
        private val _places = MutableStateFlow<List<AddPostItem.Place>>(emptyList())

        private var deletedPlaceList: AddPostItem.PlaceList? = null
        private var deletedPlaces: List<AddPostItem.Place> = emptyList()

        private val _addPostItems = MutableStateFlow<List<AddPostItem>>(emptyList())
        val addPostItems = _addPostItems.asStateFlow()

        private val _addPostState = MutableStateFlow<AddPostState>(AddPostState.Idle)
        val addPostState = _addPostState.asStateFlow()

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
                _addPostState.update {
                    if (content.content.isEmpty()) {
                        AddPostState.CannotRegister.EmptyContent
                    } else if (placeLists.any { it.name.isEmpty() }) {
                        AddPostState.CannotRegister.EmptyPlaceListName
                    } else {
                        var isAnyPlaceListEmpty = false
                        for (placeList in placeLists) {
                            if (places.none { it.placeListId == placeList.id }) {
                                isAnyPlaceListEmpty = true
                                break
                            }
                        }
                        if (isAnyPlaceListEmpty) {
                            AddPostState.CannotRegister.EmptyPlaceList
                        } else {
                            AddPostState.Idle
                        }
                    }
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

        fun rollbackDeletePlaceList() {
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

        fun rollbackDeletePlace() {
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
            viewModelScopeEH.launch {
                val userId = getUserId()
                val postContent = _content.value.content
                val placeLists =
                    mutableListOf<AddPost.PlaceList>().apply {
                        for (placeList in _placeLists.value) {
                            val places: List<AddPost.PlaceList.Place> =
                                _places.value
                                    .filter { it.placeListId == placeList.id }
                                    .map {
                                        AddPost.PlaceList.Place(
                                            placeName = it.name,
                                            placeCategoryCode = it.category.code,
                                            placeAddress = it.address,
                                            placeContent = it.content,
                                            placeImages = it.images
                                        )
                                    }
                            add(
                                AddPost.PlaceList(
                                    placeListName = placeList.name,
                                    places = places
                                )
                            )
                        }
                    }
                val addPost =
                    AddPost(
                        writerId = userId.toInt(),
                        postContent = postContent,
                        placeLists = placeLists
                    )
                if (addPost(addPost)) {
                    _addPostState.update { AddPostState.AddPostSuccess }
                } else {
                    _addPostState.update { AddPostState.AddPostFailure }
                }
            }
        }

        fun refreshState() {
            _addPostState.update { AddPostState.Idle }
        }
    }

sealed interface AddPostState {
    data object Idle : AddPostState

    sealed interface CannotRegister : AddPostState {
        val message: String

        data object EmptyContent : CannotRegister {
            override val message = "게시글 내용을 입력해주세요"
        }

        data object EmptyPlaceListName : CannotRegister {
            override val message = "장소 목록 이름을 입력해주세요"
        }

        data object EmptyPlaceList : CannotRegister {
            override val message = "장소 목록에 장소를 추가해주세요"
        }
    }

    data object AddPostSuccess : AddPostState {
        val message = "게시글이 등록되었습니다."
    }

    data object AddPostFailure : AddPostState {
        val message = "게시글 등록에 실패했습니다."
    }
}