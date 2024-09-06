package com.gangwonhyuil.gangwonhyuil.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityDetailBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity

class PlaceDetailActivity : BaseActivity<ActivityDetailBinding>() {
    private lateinit var detailItemAdapter: DetailItemAdapter

    override fun inflateBinding(inflater: LayoutInflater): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        detailItemAdapter = DetailItemAdapter()

        binding.rvPlaceDetail.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailItemAdapter
        }
    }
}