package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemCommunityBinding

class PlaceItemAdapter(
    private val postItemClickListener: OnPostItemClickListener
) : RecyclerView.Adapter<PlaceItemAdapter.PostItemHolder>() {
    private val postItems = mutableListOf<PostItem>()

    fun updateItems(newPostItems: List<PostItem>) {
        postItems.clear()
        postItems.addAll(newPostItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemHolder {
        return PostItemHolder(
            RvItemCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = postItems.size

    override fun onBindViewHolder(holder: PostItemHolder, position: Int) {
        with(postItems[position]){
            holder.bind(this)
            holder.setOnClickListener(this)
        }
    }

    inner class PostItemHolder(binding: RvItemCommunityBinding) : RecyclerView.ViewHolder(binding.root) {
        private val writerImageImageView: ImageView = binding.ivWriterImage
        private val writerNameTextView: TextView = binding.tvWriterName
        private val postContentTextView: TextView = binding.tvPostContent
        private val tvPlaceListCount: TextView = binding.tvPlaceListCount
        private val tvPlaceCount: TextView = binding.tvPlaceCount

        fun bind(postItem: PostItem) {
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