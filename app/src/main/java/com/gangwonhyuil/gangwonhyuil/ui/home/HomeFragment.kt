package com.gangwonhyuil.gangwonhyuil.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentHomeBinding
import com.gangwonhyuil.gangwonhyuil.ui.home.office.PlaceViewModel
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val placeViewModel: PlaceViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateSelectedLocationXY("강릉")
        initViewModelObserver()
        collectWeatherData()
        displayDateData()
        initLocationSpinner()
        initViewPager()
    }

    private fun collectWeatherData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherData.collect { weatherData ->
                weatherData.forEach { (category, value) ->
                    displayWeatherData(category, value)
                }
            }
        }
    }

    private fun displayDateData() {
        val today = LocalDate.now()

        val formatter = DateTimeFormatter.ofPattern("MM/dd")
        val formattedDate = today.format(formatter)
        val formattedDay = today.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

        binding.tvDate.text = "${formattedDate} (${formattedDay})"
    }

    private fun displayWeatherData(category: String, value: String) {
        when (category) {
            "POP" -> {
                binding.tvRainInfo.text = "${value}%"
            }

            "PTY" -> {
                when (value) {
                    "0" -> binding.ivWeather.setImageResource(R.drawable.ic_sunny)
                    "1" -> binding.ivWeather.setImageResource(R.drawable.ic_rainy)
                    "2" -> binding.ivWeather.setImageResource(R.drawable.ic_snowrain)
                    "3" -> binding.ivWeather.setImageResource(R.drawable.ic_snowy)
                    "4" -> binding.ivWeather.setImageResource(R.drawable.ic_rainthunder)
                }
            }
        }
    }

    private fun initLocationSpinner() {
        val items = listOf("춘천", "강릉", "동해", "영월", "속초", "원주")
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
                    parent?.getItemAtPosition(position).let {
                        viewModel.updateSelectedLocationXY(it.toString())
                        placeViewModel.updateSelectedLocation(it.toString())
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun initViewPager() {
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
            viewModel.selectedLocationXY.collect { locationXY ->
                viewModel.fetchWeatherConditions(locationXY)
            }
        }
        lifecycleScope.launch {
            placeViewModel.officeLocation.collect {
                placeViewModel.getOfficeList()
            }
        }
    }
}