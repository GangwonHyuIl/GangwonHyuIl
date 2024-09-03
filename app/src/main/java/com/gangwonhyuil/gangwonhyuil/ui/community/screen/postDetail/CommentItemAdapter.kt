package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailCommentBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class CommentItemAdapter : BaseAdapter<CommentItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        RvItemPostDetailCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<CommentItem> =
        CommentViewHolder(binding as RvItemPostDetailCommentBinding)

    inner class CommentViewHolder(
        binding: RvItemPostDetailCommentBinding,
    ) : BaseViewHolder<CommentItem>(binding.root) {
        private val ivWriterImage = binding.ivCommentWriterImage
        private val tvWriterName = binding.tvCommentWriterName
        private val tvCommentTime = binding.tvCommentTime
        private val tvCommentContent = binding.tvCommentContent

        override fun bind(item: CommentItem) {
            with(item) {
                ivWriterImage.load(writerProfileImage)
                tvWriterName.text = writerName
                tvCommentTime.text = timeStamp

                tvCommentContent.text = content
            }
        }
    }
}