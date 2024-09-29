package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserReviewsResponse
import com.gangwonhyuil.gangwonhyuil.databinding.ItemPostBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemReviewBinding

class MyReviewsListAdapter : ListAdapter<UserReviewsResponse.UserReviewsResponseItem, MyReviewsListAdapter.Holder>(
TourItemDiffCallback()
) {
    class TourItemDiffCallback : DiffUtil.ItemCallback<UserReviewsResponse.UserReviewsResponseItem>() {
        override fun areItemsTheSame(oldItem: UserReviewsResponse.UserReviewsResponseItem, newItem: UserReviewsResponse.UserReviewsResponseItem): Boolean {
            return oldItem.reviewContent == newItem.reviewContent
        }
        override fun areContentsTheSame(oldItem: UserReviewsResponse.UserReviewsResponseItem, newItem: UserReviewsResponse.UserReviewsResponseItem): Boolean {
            return oldItem.reviewContent == newItem.reviewContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RvItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.position.text = (position+1).toString()
        holder.run {
            bind(getItem(position))
        }
    }

    inner class Holder(private val binding: RvItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserReviewsResponse.UserReviewsResponseItem) {
//            Glide
//                .with(binding.root.context)
//                .load("")
//                .into(binding.imageProfile)

            binding.apply {
                tvReviewItemCreator.isGone = true
                edReviewText.text = item.reviewContent
                tvReviewScopeText.text = "목록 ${item.reviewRating}개"
            }
        }
    }
}