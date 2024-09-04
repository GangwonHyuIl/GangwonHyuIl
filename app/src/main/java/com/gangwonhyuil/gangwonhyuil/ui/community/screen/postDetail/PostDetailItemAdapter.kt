package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailCommentBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailCommentHeaderBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailHeaderBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailPlaceBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailPostBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.ImageItem.Companion.toImageItems
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class PostDetailItemAdapter : BaseAdapter<PostDetailItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        when (viewType) {
            PostDetailViewType.POST_CONTENT.type -> {
                RvItemPostDetailPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            PostDetailViewType.PLACE_HEADER.type -> {
                RvItemPostDetailHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            PostDetailViewType.PLACE_ITEM.type -> {
                RvItemPostDetailPlaceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            PostDetailViewType.COMMENT_HEADER.type -> {
                RvItemPostDetailCommentHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            PostDetailViewType.COMMENT_ITEM.type -> {
                RvItemPostDetailCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PostDetailItem> =
        when (binding) {
            is RvItemPostDetailPostBinding -> PostContentViewHolder(binding)

            is RvItemPostDetailHeaderBinding -> PlaceHeaderViewHolder(binding)

            is RvItemPostDetailPlaceBinding -> PlaceItemViewHolder(binding)

            is RvItemPostDetailCommentHeaderBinding -> CommentHeaderViewHolder(binding)

            is RvItemPostDetailCommentBinding -> CommentItemViewHolder(binding)

            else -> throw IllegalArgumentException("Invalid view type")
        } as BaseViewHolder<PostDetailItem>

    inner class PostContentViewHolder(
        binding: RvItemPostDetailPostBinding,
    ) : BaseViewHolder<PostDetailItem.PostContent>(binding.root) {
        private val ivPostWriterImage = binding.ivPostWriterImage
        private val tvPostWriterName = binding.tvPostWriterName
        private val tvPostTime = binding.tvPostTime

        private val tvPostContent = binding.tvPostContent

        override fun bind(item: PostDetailItem.PostContent) {
            with(item) {
                ivPostWriterImage.load(writerProfileImage)
                tvPostWriterName.text = writerName
                tvPostTime.text = timeStamp

                tvPostContent.text = content
            }
        }
    }

    inner class PlaceHeaderViewHolder(
        binding: RvItemPostDetailHeaderBinding,
    ) : BaseViewHolder<PostDetailItem.PlaceHeader>(binding.root) {
        private val tvPlaceListName = binding.tvPlaceListName

        override fun bind(item: PostDetailItem.PlaceHeader) {
            tvPlaceListName.text = item.placeListName
        }
    }

    inner class PlaceItemViewHolder(
        binding: RvItemPostDetailPlaceBinding,
    ) : BaseViewHolder<PostDetailItem.PlaceItem>(binding.root) {
        private val imageItemAdapter = ImageItemAdapter()

        private val tvPlaceName = binding.tvPlaceName

        private val toggleButton = binding.btnTogglePlaceDetail
        private val lyPlaceDetail = binding.lyPlaceDetail

        private val vpPlaceImage = binding.vpPlaceImage
        private val tvPlaceAddress = binding.tvPlaceAddress
        private val tvPlaceDetailContent = binding.tvPlaceDetailContent

        override fun bind(placeItem: PostDetailItem.PlaceItem) {
            with(placeItem) {
                tvPlaceName.text = name
                vpPlaceImage.adapter = imageItemAdapter
                imageItemAdapter.submitList(toImageItems(images))
                tvPlaceAddress.text = address
                tvPlaceDetailContent.text = content
            }

            setOnClickListener()
        }

        private fun setOnClickListener() {
            toggleButton.setOnClickListener {
                if (lyPlaceDetail.visibility == View.VISIBLE) {
                    lyPlaceDetail.visibility = View.GONE
                    toggleButton.setImageResource(R.drawable.ic_keyboard_arrow_up_24)
                } else {
                    lyPlaceDetail.visibility = View.VISIBLE
                    toggleButton.setImageResource(R.drawable.ic_keyboard_arrow_down_24)
                }
            }
        }
    }

    inner class CommentHeaderViewHolder(
        binding: RvItemPostDetailCommentHeaderBinding,
    ) : BaseViewHolder<PostDetailItem.CommentHeader>(binding.root) {
        override fun bind(item: PostDetailItem.CommentHeader) {
            // do nothing
        }
    }

    inner class CommentItemViewHolder(
        binding: RvItemPostDetailCommentBinding,
    ) : BaseViewHolder<PostDetailItem.CommentItem>(binding.root) {
        private val ivWriterImage = binding.ivCommentWriterImage
        private val tvWriterName = binding.tvCommentWriterName
        private val tvCommentTime = binding.tvCommentTime
        private val tvCommentContent = binding.tvCommentContent

        override fun bind(item: PostDetailItem.CommentItem) {
            with(item) {
                ivWriterImage.load(writerProfileImage)
                tvWriterName.text = writerName
                tvCommentTime.text = timeStamp

                tvCommentContent.text = content
            }
        }
    }
}