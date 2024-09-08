package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.databinding.ActivitySearchPlaceBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

const val EXTRA_PLACE_NAME_RESULT = "extra_place_name_result"
const val EXTRA_PLACE_ADDRESS_RESULT = "extra_place_address_result"

interface OnSearchResultItemClickListener {
    fun onItemClick(item: SearchResultItem)

    fun onPinClick(url: String)
}

@AndroidEntryPoint
class SearchPlaceActivity :
    BaseActivity<ActivitySearchPlaceBinding>(),
    OnSearchResultItemClickListener {
    private val viewModel: SearchPlaceViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater): ActivitySearchPlaceBinding = ActivitySearchPlaceBinding.inflate(inflater)

    private lateinit var searchResultItemAdapter: SearchResultItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModelObserver()
    }

    private fun initView() {
        initTopAppBar()
        initSearchBar()
        initSearchResultRecyclerView()
    }

    private fun initTopAppBar() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initSearchBar() {
        with(binding) {
            tvSearch.setOnClickListener {
                val keyword = etSearch.text.toString()
                if (keyword.isEmpty()) {
                    Toast
                        .makeText(this@SearchPlaceActivity, "검색 키워드를 입력해주세요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.search(keyword)
                }
            }
        }
    }

    private fun initSearchResultRecyclerView() {
        searchResultItemAdapter = SearchResultItemAdapter(this@SearchPlaceActivity)
        binding.rvSearchResult.adapter = searchResultItemAdapter
    }

    private fun initViewModelObserver() {
        with(viewModel) {
            lifecycleScope.launch {
                searchResultItems.collect {
                    searchResultItemAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClick(item: SearchResultItem) {
        // finish activity with result
        setResult(
            RESULT_OK,
            Intent().apply {
                putExtra(EXTRA_PLACE_NAME_RESULT, item.name)
                putExtra(EXTRA_PLACE_ADDRESS_RESULT, item.roadAddress)
            }
        )
        finish()
    }

    override fun onPinClick(url: String) {
        // TODO: start web view activity
        Timber.d("url: $url")
    }

    companion object {
        fun getSearchPlaceActivityIntent(context: Context): Intent = Intent(context, SearchPlaceActivity::class.java)
    }
}