package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemCommunityBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostItem
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
        private val writerProfileImageView: ImageView = binding.ivWriterProfile
        private val writerNameTextView: TextView = binding.tvWriterName
        private val postContentTextView: TextView = binding.tvPostContent
        private val tvPlaceListCount: TextView = binding.tvPlaceListCount
        private val tvPlaceCount: TextView = binding.tvPlaceCount

        override fun bind(postItem: PostItem) {
            with(postItem) {
                writerProfileImageView.load(writerInfo.profileImage)
                writerNameTextView.text = writerInfo.name

                postContentTextView.text = content
                tvPlaceListCount.text = "목록 ${placeListCount}개"
                tvPlaceCount.text = "총 장소 ${placeCount}개"
            }

            setOnClickListener(postItem)
        }

        private fun setOnClickListener(postItem: PostItem) {
            itemView.setOnClickListener {
                postItemClickListener.onPostItemClick(postItem.id)
            }
        }
    }
}