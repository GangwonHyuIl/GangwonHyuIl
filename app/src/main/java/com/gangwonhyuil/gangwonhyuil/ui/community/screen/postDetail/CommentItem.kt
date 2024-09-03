package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostComment
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.net.URL
import java.time.LocalDateTime

data class CommentItem(
    val id: Long,
    val writerId: Long,
    val writerName: String,
    val writerProfileImage: URL,
    val timeStamp: LocalDateTime,
    val content: String,
) : Eigenvalue {
    override val viewType: Int = 0
    override val eigenvalue: Any = id

    companion object {
        fun toCommentItem(comment: PostComment): CommentItem =
            CommentItem(
                id = comment.id,
                writerId = comment.writerInfo.id,
                writerName = comment.writerInfo.name,
                writerProfileImage = comment.writerInfo.profileImage,
                timeStamp = comment.timeStamp,
                content = comment.content
            )
    }
}