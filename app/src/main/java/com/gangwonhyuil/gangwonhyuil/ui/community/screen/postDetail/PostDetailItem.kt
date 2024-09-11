package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostComment
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostDetail
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.net.URL

sealed interface PostDetailItem : Eigenvalue {
    data class PostContent(
        val id: Long,
        val writerName: String,
        val writerProfileImage: String?,
        val timeStamp: String,
        val content: String,
    ) : PostDetailItem {
        override val viewType: Int = PostDetailViewType.POST_CONTENT.type
        override val eigenvalue: Any = id
    }

    data class PlaceHeader(
        val id: Long,
        val placeListName: String,
    ) : PostDetailItem {
        override val viewType: Int get() = PostDetailViewType.PLACE_HEADER.type
        override val eigenvalue get() = id
    }

    data class PlaceItem(
        val category: PlaceCategory,
        val name: String,
        val address: String,
        val images: List<URL> = emptyList(),
        val content: String = "",
    ) : PostDetailItem {
        override val viewType: Int get() = PostDetailViewType.PLACE_ITEM.type
        override val eigenvalue get() = address
    }

    data object CommentHeader : PostDetailItem {
        override val viewType: Int = PostDetailViewType.COMMENT_HEADER.type
        override val eigenvalue: Any = Unit
    }

    data class CommentItem(
        val id: Long,
        val writerId: Long,
        val writerName: String,
        val writerProfileImage: String?,
        val timeStamp: String,
        val content: String,
    ) : PostDetailItem {
        override val viewType: Int = PostDetailViewType.COMMENT_ITEM.type
        override val eigenvalue: Any = id
    }

    companion object {
        fun toPlaceItems(placeLists: List<PostDetail.PlaceList>): List<PostDetailItem> {
            val postDetailItems =
                mutableListOf<PostDetailItem>().apply {
                    for (placeList in placeLists) {
                        add(PlaceHeader(placeList.id, placeList.placeListName))
                        for (place in placeList.places) {
                            add(
                                PlaceItem(
                                    place.category,
                                    place.name,
                                    place.address,
                                    place.images,
                                    place.content
                                )
                            )
                        }
                    }
                }
            return postDetailItems
        }

        fun toCommentItems(comments: List<PostComment>): List<CommentItem> =
            comments.map {
                CommentItem(
                    id = it.id,
                    writerId = it.writerInfo.id,
                    writerName = it.writerInfo.name,
                    writerProfileImage = it.writerInfo.profileImage,
                    timeStamp = it.timeStamp,
                    content = it.content
                )
            }
    }
}