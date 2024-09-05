package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.viewbinding.ViewBinding
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPostAddPlaceBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPostAddPlaceListBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPostContentBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPostPlaceBinding
import com.gangwonhyuil.gangwonhyuil.databinding.RvItemAddPostPlaceListBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.util.base.BaseAdapter

class AddPostItemAdapter(
    private val onClickListener: OnAddPlaceItemClickListener,
) : BaseAdapter<AddPostItem>() {
    override fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding =
        when (viewType) {
            AddPostViewType.CONTENT.type ->
                RvItemAddPostContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            AddPostViewType.PLACE_LIST.type ->
                RvItemAddPostPlaceListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            AddPostViewType.PLACE.type ->
                RvItemAddPostPlaceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            AddPostViewType.ADD_PLACE.type ->
                RvItemAddPostAddPlaceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            AddPostViewType.ADD_PLACE_LIST.type ->
                RvItemAddPostAddPlaceListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

            else -> throw IllegalArgumentException("Invalid view type")
        }

    @Suppress("UNCHECKED_CAST")
    override fun createViewHolder(binding: ViewBinding): BaseViewHolder<AddPostItem> =
        when (binding) {
            is RvItemAddPostContentBinding -> ContentViewHolder(binding)
            is RvItemAddPostPlaceListBinding -> PlaceListViewHolder(binding)
            is RvItemAddPostPlaceBinding -> PlaceViewModel(binding)
            is RvItemAddPostAddPlaceBinding -> AddPlaceViewHolder(binding)
            is RvItemAddPostAddPlaceListBinding -> AddPlaceListViewHolder(binding)
            else -> throw IllegalArgumentException("Invalid binding")
        } as BaseViewHolder<AddPostItem>

    inner class ContentViewHolder(
        binding: RvItemAddPostContentBinding,
    ) : BaseViewHolder<AddPostItem.Content>(binding.root) {
        private val etContent = binding.etPostContent

        override fun bind(item: AddPostItem.Content) {
            etContent.setText(item.content)
            etContent.addTextChangedListener {
                onClickListener.onContentInput(it.toString())
            }
        }
    }

    inner class PlaceListViewHolder(
        binding: RvItemAddPostPlaceListBinding,
    ) : BaseViewHolder<AddPostItem.PlaceList>(binding.root) {
        private val etPlaceListName = binding.etPlaceListName
        private val tvDeletePlaceList = binding.tvDeletePlaceList

        override fun bind(item: AddPostItem.PlaceList) {
            with(item) {
                etPlaceListName.setText(name)
                etPlaceListName.addTextChangedListener {
                    onClickListener.onPlaceListNameInput(id, it.toString())
                }
                setOnClickListener(id)
            }
        }

        private fun setOnClickListener(placeListId: String) {
            tvDeletePlaceList.setOnClickListener {
                onClickListener.onDeletePlaceListClick(placeListId)
            }
        }
    }

    inner class PlaceViewModel(
        binding: RvItemAddPostPlaceBinding,
    ) : BaseViewHolder<AddPostItem.Place>(binding.root) {
        private val ivPlaceCategory = binding.ivPlaceCategory
        private val tvPlaceName = binding.tvPlaceName
        private val tvDeletePlace = binding.tvDeletePlace

        override fun bind(item: AddPostItem.Place) {
            with(item) {
                ivPlaceCategory.setImageResource(
                    when (category) {
                        PlaceCategory.SHARED_OFFICE -> R.drawable.ic_office_24
                        PlaceCategory.RESTAURANT -> R.drawable.ic_restaurant_24
                        PlaceCategory.CAFE -> R.drawable.ic_cafe_24
                        PlaceCategory.ACCOMODATION -> R.drawable.ic_accomodation_24
                    }
                )
                tvPlaceName.text = name

                setOnClickListener(id)
            }
        }

        private fun setOnClickListener(placeId: String) {
            tvDeletePlace.setOnClickListener {
                onClickListener.onDeletePlaceClick(placeId)
            }
        }
    }

    inner class AddPlaceViewHolder(
        binding: RvItemAddPostAddPlaceBinding,
    ) : BaseViewHolder<AddPostItem.AddPlace>(binding.root) {
        override fun bind(item: AddPostItem.AddPlace) {
            setOnClickListener(item.placeListId)
        }

        private fun setOnClickListener(placeListId: String) {
            itemView.setOnClickListener {
                onClickListener.onAddPlaceClick(placeListId)
            }
        }
    }

    inner class AddPlaceListViewHolder(
        binding: RvItemAddPostAddPlaceListBinding,
    ) : BaseViewHolder<AddPostItem.AddPlaceList>(binding.root) {
        override fun bind(item: AddPostItem.AddPlaceList) {
            setOnClickListener()
        }

        private fun setOnClickListener() {
            itemView.setOnClickListener {
                onClickListener.onAddPlaceListClick()
            }
        }
    }
}