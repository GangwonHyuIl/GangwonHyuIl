package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemSearchPlaceBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class SearchResultItemAdapter(
    private val onClickListener: OnSearchResultItemClickListener,
) : BaseAdapter<SearchResultItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        RvItemSearchPlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<SearchResultItem> =
        SearchResultItemViewHolder(binding as RvItemSearchPlaceBinding)

    inner class SearchResultItemViewHolder(
        binding: RvItemSearchPlaceBinding,
    ) : BaseViewHolder<SearchResultItem>(binding.root) {
        private val tvPlaceName = binding.tvPlaceName
        private val tvPlaceCategoryGroup = binding.tvPlaceCategoryGroup
        private val tvPlaceAddress = binding.tvPlaceAddress
        private val lyReadingGlasses = binding.lyReadingGlasses

        override fun bind(item: SearchResultItem) {
            tvPlaceName.text = item.name
            tvPlaceCategoryGroup.text = item.categoryGroup
            tvPlaceAddress.text = item.roadAddress

            setOnClickListener(item)
        }

        private fun setOnClickListener(item: SearchResultItem) {
            itemView.setOnClickListener {
                onClickListener.onItemClick(item)
            }

            lyReadingGlasses.setOnClickListener {
                onClickListener.onReadingGlassesClick(item)
            }
        }
    }
}