package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.request.post.AddPostRequest
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.AddPost
import timber.log.Timber
import javax.inject.Inject

class AddPostUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(addPost: AddPost): Boolean =
            when (addPost(addPost)) {
                is CustomResponse.Success<*> -> true
                is CustomResponse.Failure -> false
            }

        private suspend fun addPost(addPost: AddPost): CustomResponse =
            try {
                val addPostRequest =
                    AddPostRequest(
                        writerId = addPost.writerId,
                        postContent = addPost.postContent,
                        placeLists =
                            addPost.placeLists.map {
                                AddPostRequest.PlaceList(
                                    writerId = addPost.writerId,
                                    placeListName = it.placeListName,
                                    places =
                                        it.places.map { place ->
                                            AddPostRequest.PlaceList.Place(
                                                placeName = place.placeName,
                                                placeCategoryCode = place.placeCategoryCode,
                                                placeAddress = place.placeAddress,
                                                placeContent = place.placeContent,
                                                placeImages =
                                                    place.placeImages.map { url ->
                                                        AddPostRequest.PlaceList.Place.PlaceImage(url)
                                                    }
                                            )
                                        }
                                )
                            },
                        postComments = emptyList()
                    )

                CustomResponse.Success(postDataSource.addPost(addPostRequest))
            } catch (e: Exception) {
                Timber.e(e.message)
                CustomResponse.Failure(e)
            }
    }