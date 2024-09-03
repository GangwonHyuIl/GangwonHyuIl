package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.ui.community.entity.WriterInfo
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.community.PostItem
import java.net.URL
import javax.inject.Inject

class GetPostItemsUseCase
    @Inject
    constructor() {
        // return mock data
        operator fun invoke(): List<PostItem> {
            val postItems = mutableListOf<PostItem>()
            for (i in 0..10) {
                postItems.add(
                    PostItem(
                        id = i.toLong(),
                        writerInfo =
                            WriterInfo(
                                id = i.toLong(),
                                name = "작성자$i",
                                profileImage = URL("http://")
                            ),
                        timeStamp = "0000/00/00 00:00:00",
                        content = "게시글 $i 내용 게시글 $i 내용 게시글 $i 내용 게시글 $i 내용",
                        placeListCount = i * 2,
                        placeCount = i * 3
                    )
                )
            }
            return postItems
        }
    }