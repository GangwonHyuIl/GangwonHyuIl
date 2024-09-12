package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import coil.load
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPlaceImageBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPlaceImageHeaderBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class ImageItemAdapter(
    private val onClickListener: OnImageItemAdapterClickListener,
) : BaseAdapter<ImageItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        when (viewType) {
            ImageViewType.ADD_IMAGE.type ->
                RvItemAddPlaceImageHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            ImageViewType.IMAGE.type ->
                RvItemAddPlaceImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            else -> throw IllegalArgumentException("Invalid view type")
        }

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<ImageItem> =
        when (binding) {
            is RvItemAddPlaceImageHeaderBinding -> AddImageViewHolder(binding)
            is RvItemAddPlaceImageBinding -> ImageViewHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type")
        } as BaseViewHolder<ImageItem>

    inner class AddImageViewHolder(
        binding: RvItemAddPlaceImageHeaderBinding,
    ) : BaseViewHolder<ImageItem.AddImage>(binding.root) {
        override fun bind(item: ImageItem.AddImage) {
            itemView.setOnClickListener {
                onClickListener.onClickAddImage()
            }
        }
    }

    inner class ImageViewHolder(
        private val binding: RvItemAddPlaceImageBinding,
    ) : BaseViewHolder<ImageItem.Image>(binding.root) {
        override fun bind(item: ImageItem.Image) {
            binding.ivPlaceImage.load(item.imageUrl)
            itemView.setOnClickListener {
                onClickListener.onClickImage(item.imageUrl)
            }
        }
    }
}