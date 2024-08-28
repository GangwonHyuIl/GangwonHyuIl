package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.ui.community.screen.community.PostItem
import java.net.URL
import javax.inject.Inject

class GetPostItemsUseCase @Inject constructor(){
    // return mock data
    operator fun invoke(): List<PostItem> =
        listOf(
            PostItem(
                id = 0L,
                writerImage = URL("https://loremflickr.com"),
                writerName = "작성자1",
                content = "게시글 1 - 춘천에 7박 8일로 다녀온 워케이션 정보 공유합니다~",
                placeListCount = 2,
                placeCount = 3
            ),
            PostItem(
                id = 1L,
                writerImage = URL("https://loremflickr.com"),
                writerName = "작성자2",
                content = "게시글 2 - 춘천에 7박 8일로 다녀온 워케이션 정보 공유합니다~",
                placeListCount = 2,
                placeCount = 3
            ),
            PostItem(
                id = 2L,
                writerImage = URL("https://loremflickr.com"),
                writerName = "작성자3",
                content = "게시글 3 - 춘천에 7박 8일로 다녀온 워케이션 정보 공유합니다~",
                placeListCount = 2,
                placeCount = 3
            )
        )
}