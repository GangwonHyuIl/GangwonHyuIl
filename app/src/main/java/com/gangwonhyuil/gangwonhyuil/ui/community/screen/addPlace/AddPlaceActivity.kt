package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityAddPlaceBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PlaceCategory
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost.EXTRA_PLACE_RESULT
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace.EXTRA_PLACE_ADDRESS_RESULT
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace.EXTRA_PLACE_NAME_RESULT
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace.SearchPlaceActivity
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val EXTRA_PLACE_LIST_ID = "extra_place_list_id"

@AndroidEntryPoint
class AddPlaceActivity : BaseActivity<ActivityAddPlaceBinding>() {
    private val viewModel by viewModels<AddPlaceViewModel>()

    override fun inflateBinding(inflater: LayoutInflater): ActivityAddPlaceBinding = ActivityAddPlaceBinding.inflate(layoutInflater)

    private val searchPlaceActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.getStringExtra(EXTRA_PLACE_NAME_RESULT)?.let {
                    viewModel.onPlaceNameInput(it)
                }
                activityResult.data?.getStringExtra(EXTRA_PLACE_ADDRESS_RESULT)?.let {
                    viewModel.onPlaceAddressInput(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserveViewModel()
    }

    private fun initView() {
        initTopAppBar()
        initSpinner()
        initPlaceInfoLayout()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setNavigationOnClickListener {
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.add_place -> {
                        val newPlace = viewModel.createPlace()
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

    private fun initSpinner() {
        with(binding.spinnerPlaceCategory) {
            adapter =
                ArrayAdapter(
                    this@AddPlaceActivity,
                    R.layout.spinner_item_place_category,
                    PlaceCategory.entries.map { it.category }
                )
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long,
                    ) {
                        viewModel.onSelectPlaceCategory(PlaceCategory.entries[p2])
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        // do nothing
                    }
                }
        }
    }

    private fun initPlaceInfoLayout() {
        with(binding) {
            tvSearchPlace.setOnClickListener {
                searchPlaceActivityLauncher.launch(
                    SearchPlaceActivity.getSearchPlaceActivityIntent(this@AddPlaceActivity)
                )
            }
        }
    }

    private fun initObserveViewModel() {
        with(viewModel) {
            lifecycleScope.launch {
                placeName.collect {
                    binding.etPlaceName.setText(it)
                }
            }
            lifecycleScope.launch {
                placeAddress.collect {
                    binding.etPlaceAddress.setText(it)
                }
            }
        }
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