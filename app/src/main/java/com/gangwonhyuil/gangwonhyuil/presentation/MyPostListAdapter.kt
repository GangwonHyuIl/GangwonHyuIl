package com.gangwonhyuil.gangwonhyuil.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gangwonhyuil.gangwonhyuil.data.remote.post.Post
import com.gangwonhyuil.gangwonhyuil.databinding.ItemPostBinding

class MyPostListAdapter : ListAdapter<Post, MyPostListAdapter.Holder>(
TourItemDiffCallback()
) {
    class TourItemDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postTitle == newItem.postTitle
        }
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
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
        fun bind(item: Post) {
            Glide
                .with(binding.root.context)
                .load("")
                .into(binding.imageProfile)

            binding.apply {
                textPostWriter.text = item.writerName
                textPostTitle.text = item.postTitle
                textListCount.text = "목록 ${item.placeListCount}개"
                textPlaceCount.text = "춍 장소 ${item.placeCount}개"
            }
        }
    }
}