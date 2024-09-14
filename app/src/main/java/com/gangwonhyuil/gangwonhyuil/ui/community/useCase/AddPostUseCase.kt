package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.request.post.AddPostRequest
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost.AddPostItem
import timber.log.Timber
import javax.inject.Inject

class AddPostUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(
            userId: Long,
            addPostItems: List<AddPostItem>,
        ): Boolean =
            when (addPost(userId, addPostItems)) {
                is CustomResponse.Success<*> -> true
                is CustomResponse.Failure -> false
            }

        private suspend fun addPost(
            userId: Long,
            addPostItems: List<AddPostItem>,
        ): CustomResponse =
            try {
                val postContent: String =
                    (addPostItems.find { it is AddPostItem.Content } as? AddPostItem.Content)?.content
                        ?: throw IllegalArgumentException("no post content")
                val placeLists = mutableListOf<AddPostRequest.PlaceList>()
                for (placeList in addPostItems.filterIsInstance<AddPostItem.PlaceList>()) {
                    val places: List<AddPostRequest.PlaceList.Place> =
                        addPostItems
                            .filterIsInstance<AddPostItem.Place>()
                            .filter { it.placeListId == placeList.id }
                            .map {
                                AddPostRequest.PlaceList.Place(
                                    placeName = it.name,
                                    placeCategoryCode = it.category.code,
                                    placeAddress = it.address,
                                    placeContent = it.content,
                                    placeImages =
                                        it.images.map { url ->
                                            AddPostRequest.PlaceList.Place.PlaceImage(url)
                                        }
                                )
                            }
                    placeLists.add(
                        AddPostRequest.PlaceList(
                            placeListName = placeList.name,
                            places = places,
                            writerId = userId.toInt()
                        )
                    )
                }

                val addPostRequest =
                    AddPostRequest(
                        writerId = userId.toInt(),
                        postContent = postContent,
                        placeLists = placeLists,
                        postComments = emptyList()
                    )

                CustomResponse.Success(postDataSource.addPost(addPostRequest))
            } catch (e: Exception) {
                Timber.e(e.message)
                CustomResponse.Failure(e)
            }
    }