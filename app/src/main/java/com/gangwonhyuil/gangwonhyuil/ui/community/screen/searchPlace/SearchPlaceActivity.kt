package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.gangwonhyuil.gangwonhyuil.databinding.ActivitySearchPlaceBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlaceDetail.SearchPlaceDetailActivity
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val EXTRA_PLACE_NAME_RESULT = "extra_place_name_result"
const val EXTRA_PLACE_ADDRESS_RESULT = "extra_place_address_result"

interface OnSearchResultItemClickListener {
    fun onItemClick(item: SearchResultItem)

    fun onReadingGlassesClick(item: SearchResultItem)
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
                    val inputMethodManager =
                        this@SearchPlaceActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    with(binding.etSearch) {
                        clearFocus()
                        inputMethodManager.hideSoftInputFromWindow(
                            windowToken,
                            0
                        )
                    }

                    viewModel.search(keyword)
                }
            }
        }
    }

    private fun initSearchResultRecyclerView() {
        searchResultItemAdapter = SearchResultItemAdapter(this@SearchPlaceActivity)
        with(binding.rvSearchResult) {
            adapter = searchResultItemAdapter
            animation = null
            addOnScrollListener(
                object : OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int,
                    ) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (lastVisibleItemPosition == searchResultItemAdapter.itemCount - 1) viewModel.searchMore()
                    }
                }
            )
        }
    }

    private fun initViewModelObserver() {
        with(viewModel) {
            lifecycleScope.launch {
                searchResultItems.collect {
                    searchResultItemAdapter.submitList(it)
                }
            }

            lifecycleScope.launch {
                searchState.collect {
                    when (it) {
                        SearchState.Idle -> {
                            binding.rvSearchResult.visibility = View.VISIBLE
                            binding.tvNoResult.visibility = View.GONE
                        }

                        SearchState.NoResult -> {
                            binding.rvSearchResult.visibility = View.GONE
                            binding.tvNoResult.visibility = View.VISIBLE
                        }
                    }
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

    override fun onReadingGlassesClick(item: SearchResultItem) {
        startActivity(
            SearchPlaceDetailActivity.getPlaceWebViewActivityIntent(
                context = this@SearchPlaceActivity,
                placeName = item.name,
                placeUrl = item.url
            )
        )
    }

    companion object {
        fun getSearchPlaceActivityIntent(context: Context): Intent = Intent(context, SearchPlaceActivity::class.java)
    }
}