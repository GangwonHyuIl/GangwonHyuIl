package com.gangwonhyuil.gangwonhyuil.ui.home.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityDetailBinding
import com.gangwonhyuil.gangwonhyuil.ui.home.office.EXTRA_PLACE_ID
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailActivity : BaseActivity<ActivityDetailBinding>() {
    private lateinit var placeItemDetailAdapter: PlaceItemDetailAdapter

    override fun inflateBinding(inflater: LayoutInflater): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()
        initRecyclerView()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.title = "새로운 타이틀"
    }

    private fun initRecyclerView() {
        placeItemDetailAdapter = PlaceItemDetailAdapter()

        binding.rvPlaceDetail.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeItemDetailAdapter
        }
    }

    companion object {
        fun getPlaceDetailActivityIntent(
            context: Context,
            postId: Long,
        ): Intent =
            Intent(context, PlaceDetailActivity::class.java).apply {
                putExtra(EXTRA_PLACE_ID, postId)
            }
    }
}