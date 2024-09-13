package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.util.UUID

sealed interface AddPostItem : Eigenvalue {
    data class Content(
        val content: String,
    ) : AddPostItem {
        override val viewType: Int = AddPostViewType.CONTENT.type
        override val eigenvalue: Any = Unit
    }

    data class PlaceList(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
    ) : AddPostItem {
        override val viewType: Int = AddPostViewType.PLACE_LIST.type
        override val eigenvalue: Any = id
    }

    data class Place(
        val placeListId: String,
        val id: String = UUID.randomUUID().toString(),
        val category: PlaceCategory,
        val name: String,
        val address: String,
        val images: List<String>,
        val content: String,
    ) : AddPostItem {
        override val viewType: Int = AddPostViewType.PLACE.type
        override val eigenvalue: Any = id
    }

    data class AddPlace(
        val placeListId: String,
    ) : AddPostItem {
        override val viewType: Int = AddPostViewType.ADD_PLACE.type
        override val eigenvalue: Any = placeListId
    }

    data object AddPlaceList : AddPostItem {
        override val viewType: Int = AddPostViewType.ADD_PLACE_LIST.type
        override val eigenvalue: Any = Unit
    }
}