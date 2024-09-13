package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.VpItemPlaceDetailImageBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class ImageItemAdapter : BaseAdapter<ImageItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        VpItemPlaceDetailImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<ImageItem> =
        ImageItemViewHolder(binding as VpItemPlaceDetailImageBinding)

    inner class ImageItemViewHolder(
        binding: VpItemPlaceDetailImageBinding,
    ) : BaseViewHolder<ImageItem>(binding.root) {
        private val ivPlaceImage = binding.ivPlaceImage

        override fun bind(item: ImageItem) {
            ivPlaceImage.load(item.url)
        }
    }
}