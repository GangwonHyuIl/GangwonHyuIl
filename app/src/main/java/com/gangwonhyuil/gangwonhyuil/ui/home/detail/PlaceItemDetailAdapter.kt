package com.gangwonhyuil.gangwonhyuil.ui.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPlaceDetailImageBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemPlaceDetailInfoBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class PlaceItemDetailAdapter(
) : BaseAdapter<PlaceDetailItem>() {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewBinding {
        return when (viewType) {
            PlaceDetailViewType.PLACE_CONTENT.type -> {
                RvItemPlaceDetailInfoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            }

            PlaceDetailViewType.PLACE_IMAGE.type -> {
                RvItemPlaceDetailImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<PlaceDetailItem> {
        return when (binding) {
            is RvItemPlaceDetailInfoBinding -> DetailInfoHolder(binding)
            is RvItemPlaceDetailImageBinding -> DetailImageHolder(binding)
            else -> throw IllegalArgumentException("Invalid binding")
        }
    }


    inner class DetailInfoHolder(binding: RvItemPlaceDetailInfoBinding) :
        BaseViewHolder<PlaceDetailItem>(binding.root) {
        private val officeRating = binding.tvItemRating
        private val officeAddress = binding.tvAddress
        private val officeBusinessHour = binding.tvBusinessHour
        private val officeNumber = binding.tvPhoneNumber
        private val reviewButton = binding.cvReview

        override fun bind(item: PlaceDetailItem) {
            if (item is PlaceDetailItem.PlaceInfo) {
                officeAddress.text = item.address
                officeBusinessHour.text = item.businessHour
                officeNumber.text = item.phone
            }
        }

    }

    inner class DetailImageHolder(binding: RvItemPlaceDetailImageBinding) :
        BaseViewHolder<PlaceDetailItem>(binding.root) {
        private val officeImageView = binding.ivItemImage

        override fun bind(item: PlaceDetailItem) {
            if (item is PlaceDetailItem.PlaceImage) {
                officeImageView.load(item.image) {
                    crossfade(true)
                }
            }
        }

    }
}