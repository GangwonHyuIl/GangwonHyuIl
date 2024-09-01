package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemCommunityBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class PlaceItemAdapter(
    private val postItemClickListener: OnPostItemClickListener,
) : BaseAdapter<PostItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        RvItemCommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PostItem> = PostItemHolder(binding as RvItemCommunityBinding)

    inner class PostItemHolder(
        binding: RvItemCommunityBinding,
    ) : BaseViewHolder<PostItem>(binding.root) {
        private val writerImageImageView: ImageView = binding.ivWriterImage
        private val writerNameTextView: TextView = binding.tvWriterName
        private val postContentTextView: TextView = binding.tvPostContent
        private val tvPlaceListCount: TextView = binding.tvPlaceListCount
        private val tvPlaceCount: TextView = binding.tvPlaceCount

        override fun bind(postItem: PostItem) {
            // TODO: set writer image image view
            writerNameTextView.text = postItem.writerName
            postContentTextView.text = postItem.content
            tvPlaceListCount.text = "목록 ${postItem.placeListCount}개"
            tvPlaceCount.text = "총 장소 ${postItem.placeCount}개"
        }

        fun setOnClickListener(postItem: PostItem) {
            itemView.setOnClickListener {
                postItemClickListener.onPostItemClick(postItem.id)
            }
        }
    }
}