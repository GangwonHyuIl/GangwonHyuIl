package com.gangwonhyuil.gangwonhyuil.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentHomeBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setupLocationSpinner()
    }

    private fun setupLocationSpinner() {
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
                    Log.d("selectedItem", selectedItem)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }
}