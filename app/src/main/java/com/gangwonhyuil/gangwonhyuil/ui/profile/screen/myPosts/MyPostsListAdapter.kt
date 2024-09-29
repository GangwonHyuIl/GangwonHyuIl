package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserPostsResponse.UserPostsResponseItem
import com.gangwonhyuil.gangwonhyuil.databinding.ItemPostBinding

class MyPostsListAdapter : ListAdapter<UserPostsResponseItem, MyPostsListAdapter.Holder>(
TourItemDiffCallback()
) {
    class TourItemDiffCallback : DiffUtil.ItemCallback<UserPostsResponseItem>() {
        override fun areItemsTheSame(oldItem: UserPostsResponseItem, newItem: UserPostsResponseItem): Boolean {
            return oldItem.postTitle == newItem.postTitle
        }
        override fun areContentsTheSame(oldItem: UserPostsResponseItem, newItem: UserPostsResponseItem): Boolean {
            return oldItem.postTitle == newItem.postTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.position.text = (position+1).toString()
        holder.run {
            bind(getItem(position))
        }
    }

    inner class Holder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserPostsResponseItem) {
//            Glide
//                .with(binding.root.context)
//                .load("")
//                .into(binding.imageProfile)

            binding.apply {
                textPostWriter.text = item.postTitle
                textPostTitle.text = item.postTitle
                textListCount.text = "목록 ${item.categoryCount}개"
                textPlaceCount.text = "춍 장소 ${item.contentsCount}개"
            }
        }
    }
}