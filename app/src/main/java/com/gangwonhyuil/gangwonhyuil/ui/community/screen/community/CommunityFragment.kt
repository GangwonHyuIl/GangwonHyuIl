package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentCommunityBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

interface OnPostItemClickListener {
    fun onPostItemClick(id: Long)
}

@AndroidEntryPoint
class CommunityFragment :
    BaseFragment<FragmentCommunityBinding>(),
    OnPostItemClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this@CommunityFragment)[CommunityViewModel::class.java]
    }

    private lateinit var placeItemAdapter: PlaceItemAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCommunityBinding = FragmentCommunityBinding.inflate(inflater, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initTopAppBar()
        initPlaceItemRecyclerView()
        initViewModelObserver()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.add_post -> {
                        // TODO: start add post activity
                        Timber.d("add post menu clicked")
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initPlaceItemRecyclerView() {
        placeItemAdapter = PlaceItemAdapter(this@CommunityFragment)
        binding.rvPost.adapter = placeItemAdapter
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.postItems.collectLatest { postItems ->
                placeItemAdapter.submitList(postItems)
            }
        }
    }

    override fun onPostItemClick(id: Long) {
        // TODO: start post detail activity, send post id
        Timber.d("post item clicked) id: $id")
    }
}