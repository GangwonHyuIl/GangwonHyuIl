package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostDetailResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostDetail
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.WriterInfo
import timber.log.Timber
import javax.inject.Inject

class GetPostDetailUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(postId: Long): PostDetail? =
            when (val customResponse = getPostDetail(postId)) {
                is CustomResponse.Success<*> -> {
                    with(customResponse.data as GetPostDetailResponse) {
                        PostDetail(
                            id = postIdx.toLong(),
                            writerInfo =
                                WriterInfo(
                                    id = writerIdx.toLong(),
                                    name = writerName,
                                    profileImage = writerProfileImage
                                ),
                            timeStamp = postCreatedAt,
                            content = postTitle,
                            placeList =
                                placeLists?.map {
                                    PostDetail.PlaceList(
                                        id = it.placeListIdx.toLong(),
                                        placeListName = it.placeListName,
                                        places =
                                            it.places?.map { place ->
                                                PostDetail.PlaceList.Place(
                                                    id = place.placeIdx.toLong(),
                                                    category = PlaceCategory.fromCode(place.placeCategoryCode),
                                                    name = place.placeTitle,
                                                    address = place.placeAddress,
                                                    images =
                                                        place.placeImages?.map { image -> image.imageUrl }
                                                            ?: emptyList(),
                                                    content = place.placeContent ?: ""
                                                )
                                            } ?: emptyList()
                                    )
                                } ?: emptyList(),
                            comments =
                                postComments?.map {
                                    PostDetail.PostComment(
                                        id = it.commentIdx.toLong(),
                                        writerInfo =
                                            WriterInfo(
                                                id = it.writerIdx.toLong(),
                                                name = it.writerName ?: "",
                                                profileImage = it.writerProfileImage
                                            ),
                                        timeStamp = it.createdAt,
                                        content = it.content
                                    )
                                } ?: emptyList()
                        )
                    }
                }

                is CustomResponse.Failure -> {
                    Timber.e(customResponse.e)
                    null
                }
            }

        private suspend fun getPostDetail(postId: Long): CustomResponse =
            try {
                val response = postDataSource.getPostDetail("eq.$postId")
                CustomResponse.Success(response.first())
            } catch (e: Exception) {
                CustomResponse.Failure(e)
            }
    }