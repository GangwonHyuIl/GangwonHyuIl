package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import coil.load
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

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PostItem> = PostItemViewHolder(binding as RvItemCommunityBinding)

    inner class PostItemViewHolder(
        binding: RvItemCommunityBinding,
    ) : BaseViewHolder<PostItem>(binding.root) {
        private val ivWriterProfile: ImageView = binding.ivWriterProfile
        private val tvWriterName: TextView = binding.tvWriterName
        private val tvPostTime: TextView = binding.tvPostTime

        private val tvPostContent: TextView = binding.tvPostContent
        private val tvPlaceListCount: TextView = binding.tvPlaceListCount
        private val tvPlaceCount: TextView = binding.tvPlaceCount

        override fun bind(postItem: PostItem) {
            with(postItem) {
                ivWriterProfile.load(writerInfo.profileImage)
                tvWriterName.text = writerInfo.name
                tvPostTime.text = timeStamp

                tvPostContent.text = content
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