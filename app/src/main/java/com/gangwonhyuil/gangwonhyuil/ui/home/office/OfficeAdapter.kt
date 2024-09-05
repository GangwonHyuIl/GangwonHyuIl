package com.gangwonhyuil.gangwonhyuil.ui.home.office

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemOfficeBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class OfficeAdapter : BaseAdapter<Office>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        RvItemOfficeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<Office> = OfficeItemHolder(binding as RvItemOfficeBinding)

    inner class OfficeItemHolder(
        binding: RvItemOfficeBinding,
    ) : BaseViewHolder<Office>(binding.root) {
        private val officeImageView = binding.ivItemImage
        private val officeNameTextView = binding.tvItemName
        private val officeRatingTextView = binding.tvItemRating

        override fun bind(item: Office) {
            if (item is Office.OfficeItem) {
                officeImageView.load(item.image) {
                    crossfade(true)
                }
                officeNameTextView.text = item.name
                officeRatingTextView.text = item.rating
            }
        }
    }
}