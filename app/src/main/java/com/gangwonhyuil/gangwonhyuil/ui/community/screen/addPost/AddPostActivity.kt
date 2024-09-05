package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityAddPostBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

interface OnAddPlaceItemClickListener {
    fun onContentInput(content: String)

    fun onPlaceListNameInput(
        id: String,
        name: String,
    )

    fun onDeletePlaceListClick(placeListId: String)

    fun onAddPlaceClick(placeListId: String)

    fun onDeletePlaceClick(placeId: String)

    fun onAddPlaceListClick()
}

@AndroidEntryPoint
class AddPostActivity :
    BaseActivity<ActivityAddPostBinding>(),
    OnAddPlaceItemClickListener {
    private val viewModel by viewModels<AddPostViewModel>()

    private lateinit var placeListItemAdapter: AddPostItemAdapter

    override fun inflateBinding(inflater: LayoutInflater): ActivityAddPostBinding = ActivityAddPostBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModelObserver()
    }

    private fun initView() {
        initTopAppBar()
        initPlaceListItemRecyclerView()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setNavigationOnClickListener {
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.register_post -> {
                        viewModel.registerPost()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun initPlaceListItemRecyclerView() {
        placeListItemAdapter = AddPostItemAdapter(this@AddPostActivity)
        binding.rvPlaceList.adapter = placeListItemAdapter
    }

    private fun initViewModelObserver() {
        with(viewModel) {
            lifecycleScope.launch {
                addPostItems.collect { addPostItems ->
                    placeListItemAdapter.submitList(addPostItems)
                }
            }
        }
    }

    override fun onContentInput(content: String) {
        Timber.d("onContentInput, content: $content")
        viewModel.onContentInput(content)
    }

    override fun onPlaceListNameInput(
        id: String,
        name: String,
    ) {
        Timber.d("onPlaceListNameInput, name: $name")
        viewModel.onPlaceListNameInput(id, name)
    }

    override fun onDeletePlaceListClick(placeListId: String) {
        viewModel.onDeletePlaceList(placeListId)
    }

    override fun onAddPlaceClick(placeListId: String) {
        // TODO: start add place activity
        // TODO: save added place on activity finish
        Timber.d("onAddPlaceClick, placeListId: $placeListId")
    }

    override fun onDeletePlaceClick(placeId: String) {
        viewModel.onDeletePlace(placeId)
    }

    override fun onAddPlaceListClick() {
        viewModel.onAddPlaceList()
    }

    companion object {
        fun getAddPostActivityIntent(context: Context): Intent {
            Timber.d("startAddPostActivity")
            return Intent(context, AddPostActivity::class.java)
        }
    }
}