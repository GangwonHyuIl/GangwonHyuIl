package com.gangwonhyuil.gangwonhyuil.ui.home.office

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentPlaceInfoBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OfficeInfoFragment : BaseFragment<FragmentPlaceInfoBinding>() {
    private val viewModel: OfficeViewModel by viewModels()
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

        setCategoryTitle()
        initRecyclerView()
        observeViewModel()
    }

    private fun setCategoryTitle() {
        val category = arguments?.getString(ARG_CATEGORY) ?: "카테고리 없음"
        Timber.d("카테고리: $category")
        viewModel.fetchAreaBasedRestaurantList(category)
    }

    private fun initRecyclerView() {
        placeAdapter = PlaceAdapter()

        binding.rvOffice.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.itemList.collect { officeList ->
                placeAdapter.submitList(officeList)
            }
        }
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String): OfficeInfoFragment {
            val fragment = OfficeInfoFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}