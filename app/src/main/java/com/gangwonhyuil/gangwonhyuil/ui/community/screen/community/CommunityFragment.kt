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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface OnPostItemClickListener{
    fun onPostItemClick(id: Long)
}

@AndroidEntryPoint
class CommunityFragment : Fragment(), OnPostItemClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this@CommunityFragment)[CommunityViewModel::class.java]
    }

    private lateinit var placeItemAdapter: PlaceItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initViewModelObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    private fun initAdapter(){
        placeItemAdapter = PlaceItemAdapter(this@CommunityFragment)
    }

    private fun initViewModelObserver(){
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