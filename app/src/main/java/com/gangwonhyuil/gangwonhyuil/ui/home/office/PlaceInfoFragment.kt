package com.gangwonhyuil.gangwonhyuil.ui.home.office

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentPlaceInfoBinding
import com.gangwonhyuil.gangwonhyuil.ui.home.detail.PlaceDetailActivity.Companion.getPlaceDetailActivityIntent
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

interface OnPlaceItemClickListener {
    fun onPlaceItemClick(id: Long)
}

@AndroidEntryPoint
class PlaceInfoFragment : BaseFragment<FragmentPlaceInfoBinding>(),
    OnPlaceItemClickListener {
    private val viewModel: PlaceViewModel by activityViewModels()
    private lateinit var placeAdapter: PlaceAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentPlaceInfoBinding = FragmentPlaceInfoBinding.inflate(inflater, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        placeAdapter = PlaceAdapter(this@PlaceInfoFragment)

        binding.rvOffice.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.itemList.collect { itemList ->
                val currentCategory = arguments?.getString(ARG_CATEGORY) ?: "숙소"
                val itemsForCategory = itemList[currentCategory] ?: emptyList()
                placeAdapter.submitList(itemsForCategory)
            }
        }
        lifecycleScope.launch {
            viewModel.sigunguCode.collect {
                viewModel.fetchAllCategories()
            }
        }
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String): PlaceInfoFragment {
            val fragment = PlaceInfoFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onPlaceItemClick(id: Long) {
        Timber.d("place item clicked) id: $id")
        startActivity(
            getPlaceDetailActivityIntent(
                requireContext(),
                id
            )
        )
    }
}