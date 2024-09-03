package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import com.gangwonhyuil.gangwonhyuil.ui.community.model.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostDetail
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.net.URL

sealed interface PlaceItem : Eigenvalue {
    data class Header(
        val id: Long,
        val placeListName: String,
    ) : PlaceItem {
        override val viewType: Int get() = ViewType.HEADER.type
        override val eigenvalue get() = id
    }

    data class Item(
        val category: PlaceCategory,
        val name: String,
        val address: String,
        val images: List<URL> = emptyList(),
        val content: String = "",
    ) : PlaceItem {
        override val viewType: Int get() = ViewType.ITEM.type
        override val eigenvalue get() = address
    }

    enum class ViewType(
        val type: Int,
    ) {
        HEADER(0),
        ITEM(1),
    }

    companion object {
        fun toPlaceItems(placeLists: List<PostDetail.PlaceList>): List<PlaceItem> {
            val placeItems =
                mutableListOf<PlaceItem>().apply {
                    for (placeList in placeLists) {
                        add(Header(placeList.id, placeList.placeListName))
                        for (place in placeList.places) {
                            add(
                                Item(
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
            return placeItems
        }
    }
}