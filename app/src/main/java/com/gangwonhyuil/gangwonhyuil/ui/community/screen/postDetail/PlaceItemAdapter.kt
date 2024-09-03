package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailHeaderBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPostDetailPlaceBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class PlaceItemAdapter : BaseAdapter<PlaceItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        when (viewType) {
            PlaceItem.ViewType.HEADER.type -> {
                RvItemPostDetailHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            PlaceItem.ViewType.ITEM.type -> {
                RvItemPostDetailPlaceBinding.inflate(
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
    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PlaceItem> =
        when (binding) {
            is RvItemPostDetailHeaderBinding -> HeaderViewHolder(binding) as BaseViewHolder<PlaceItem>

            is RvItemPostDetailPlaceBinding -> ItemViewHolder(binding) as BaseViewHolder<PlaceItem>

            else -> throw IllegalArgumentException("Invalid view type")
        }

    inner class HeaderViewHolder(
        binding: RvItemPostDetailHeaderBinding,
    ) : BaseViewHolder<PlaceItem.Header>(binding.root) {
        private val tvPlaceListName = binding.tvPlaceListName

        override fun bind(item: PlaceItem.Header) {
            tvPlaceListName.text = item.placeListName
        }
    }

    inner class ItemViewHolder(
        binding: RvItemPostDetailPlaceBinding,
    ) : BaseViewHolder<PlaceItem.Item>(binding.root) {
        private val tvPlaceName = binding.tvPlaceName

        private val toggleButton = binding.btnTogglePlaceDetail
        private val lyPlaceDetail = binding.lyPlaceDetail

        private val vpPlaceImage = binding.vpPlaceImage
        private val tvPlaceAddress = binding.tvPlaceAddress
        private val tvPlaceDetailContent = binding.tvPlaceDetailContent

        override fun bind(item: PlaceItem.Item) {
            with(item) {
                tvPlaceName.text = name
                // TODO: set place image view pager
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
}