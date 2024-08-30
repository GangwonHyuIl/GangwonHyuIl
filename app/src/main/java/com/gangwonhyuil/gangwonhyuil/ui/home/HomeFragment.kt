package com.gangwonhyuil.gangwonhyuil.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentHomeBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        initLocationSpinner()
        initViewModelObserver()
    }

    private fun initLocationSpinner() {
        val items = listOf("강릉", "속초", "동해", "춘천")
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner_item, items)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item)
        binding.locationSpinner.adapter = spinnerAdapter

        binding.locationSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    viewModel.updateSelectedLocation(selectedItem)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun setupViewPager() {
        val viewPager = binding.vpViewpagerMain
        val tabLayout = binding.tlTabLayout

        viewPager.adapter = HomePagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "공유 오피스"
                1 -> "숙소"
                2 -> "식당"
                3 -> "카페"
                else -> null
            }
        }.attach()
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.selectedLocation.collect { location ->

            }
        }
    }
}