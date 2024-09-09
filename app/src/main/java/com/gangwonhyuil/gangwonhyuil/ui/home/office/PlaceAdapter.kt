package com.gangwonhyuil.gangwonhyuil.ui.home.office

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPlaceBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class PlaceAdapter(
    private val placeItemClickListener: OnPlaceItemClickListener,
) : BaseAdapter<PlaceItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        RvItemPlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PlaceItem> =
        OfficeItemHolder(binding as RvItemPlaceBinding)

    inner class OfficeItemHolder(
        binding: RvItemPlaceBinding,
    ) : BaseViewHolder<PlaceItem>(binding.root) {
        private val officeImageView = binding.ivItemImage
        private val officeNameTextView = binding.tvItemName
        private val officeRatingTextView = binding.tvItemRating

        override fun bind(placeItem: PlaceItem) {
            officeImageView.load(placeItem.image) {
                crossfade(true)
            }
            officeNameTextView.text = placeItem.name
//                officeRatingTextView.text = item.rating

            setOnClickListener(placeItem)
        }

        private fun setOnClickListener(placeItem: PlaceItem) {
            itemView.setOnClickListener {
                placeItemClickListener.onPlaceItemClick(placeItem.id)
            }
        }
    }
}