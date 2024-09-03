package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostComment
import com.gangwonhyuil.gangwonhyuil.ui.community.model.WriterInfo
import java.net.URL
import javax.inject.Inject

class GetPostCommentsUseCase
    @Inject
    constructor() {
        // return mock data
        operator fun invoke(postId: Long): List<PostComment> {
            val postComments = mutableListOf<PostComment>()
            for (i in 0..5) {
                postComments.add(
                    PostComment(
                        id = i.toLong(),
                        writerInfo =
                            WriterInfo(
                                id = i.toLong(),
                                name = "작성자$i",
                                profileImage = URL("http://")
                            ),
                        timeStamp = "0000/00/00 00:00:00",
                        content = "댓글$i 내용 댓글$i 내용 댓글$i 내용댓글$i 내용 댓글$i 내용"
                    )
                )
            }
            return postComments
        }
    }