package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.ui.community.model.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostDetail
import com.gangwonhyuil.gangwonhyuil.ui.community.model.WriterInfo
import java.net.URL
import javax.inject.Inject

class GetPostDetailUseCase
    @Inject
    constructor() {
        // return mock data
        operator fun invoke(postId: Long): PostDetail =
            PostDetail(
                id = 0L,
                writerInfo =
                    WriterInfo(
                        id = 0L,
                        name = "작성자1",
                        profileImage = URL("http://")
                    ),
                timeStamp = "0000/00/00 00:00:00",
                content = "춘천에 7박 8일로 다녀온 워케이션 정보 공유합니다~",
                placeList =
                    listOf(
                        PostDetail.PlaceList(
                            id = 0L,
                            placeListName = "공유오피스 목록",
                            places =
                                listOf(
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.SHARED_OFFICE,
                                        name = "공유 오피스 1",
                                        address = "공유 오피스 주소 1",
                                        images = listOf(URL("http://")),
                                        content = "너무 조용하고 깔끔했던 공유오피스 입니다. 업무 집중이 잘 되었어요 :)"
                                    ),
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.SHARED_OFFICE,
                                        name = "공유 오피스 2",
                                        address = "공유 오피스 주소 2",
                                        images = listOf(URL("http://")),
                                        content = "너무 조용하고 깔끔했던 공유오피스 입니다. 업무 집중이 잘 되었어요 :)"
                                    ),
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.SHARED_OFFICE,
                                        name = "공유 오피스 3",
                                        address = "공유 오피스 주소 3",
                                        images = listOf(URL("http://")),
                                        content = "너무 조용하고 깔끔했던 공유오피스 입니다. 업무 집중이 잘 되었어요 :)"
                                    )
                                )
                        ),
                        PostDetail.PlaceList(
                            id = 1L,
                            placeListName = "식당 & 카페 목록",
                            places =
                                listOf(
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.RESTAURANT,
                                        name = "식당 1",
                                        address = "식당 주소 1",
                                        images = listOf(URL("http://")),
                                        content = "춘천 맛집입니다! 꼭 방문하시길~"
                                    ),
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.RESTAURANT,
                                        name = "식당 2",
                                        address = "식당 주소 2",
                                        images = listOf(URL("http://")),
                                        content = "춘천 맛집입니다! 꼭 방문하시길~"
                                    ),
                                    PostDetail.PlaceList.Place(
                                        category = PlaceCategory.CAFE,
                                        name = "카페1",
                                        address = "카페 주소 1",
                                        images = listOf(URL("http://")),
                                        content = "춘천 갬카입니다! 꼭 방문하시길~"
                                    )
                                )
                        )
                    )
            )
    }