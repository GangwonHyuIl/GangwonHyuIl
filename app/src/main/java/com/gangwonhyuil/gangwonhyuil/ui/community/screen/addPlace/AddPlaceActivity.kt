package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityAddPlaceBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost.EXTRA_PLACE_RESULT
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_PLACE_LIST_ID = "extra_place_list_id"

@AndroidEntryPoint
class AddPlaceActivity : BaseActivity<ActivityAddPlaceBinding>() {
    private val viewModel by viewModels<AddPlaceViewModel>()

    override fun inflateBinding(inflater: LayoutInflater): ActivityAddPlaceBinding = ActivityAddPlaceBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserveViewModel()
    }

    private fun initView() {
        initTopAppBar()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setNavigationOnClickListener {
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.add_place -> {
                        val newPlace = viewModel.getNewPlace()
                        setResult(
                            RESULT_OK,
                            Intent().apply {
                                Gson().toJson(newPlace)?.let {
                                    putExtra(EXTRA_PLACE_RESULT, it)
                                }
                            }
                        )
                        finish()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun initObserveViewModel() {
    }

    companion object {
        fun getAddPlaceActivityIntent(
            context: Context,
            placeListId: String,
        ): Intent =
            Intent(context, AddPlaceActivity::class.java).apply {
                putExtra(EXTRA_PLACE_LIST_ID, placeListId)
            }
    }
}