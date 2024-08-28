package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentCommunityBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface OnPostItemClickListener {
    fun onPostItemClick(id: Long)
}

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(), OnPostItemClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this@CommunityFragment)[CommunityViewModel::class.java]
    }

    private lateinit var placeItemAdapter: PlaceItemAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewModelObserver()
    }

    private fun initAdapter() {
        placeItemAdapter = PlaceItemAdapter(this@CommunityFragment)
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.postItems.collectLatest { postItems ->
                placeItemAdapter.updateItems(postItems)
            }
        }
    }

    override fun onPostItemClick(id: Long) {
        // TODO: start post detail activity
        Toast.makeText(requireContext(), "id: $id", Toast.LENGTH_SHORT).show()
    }
}