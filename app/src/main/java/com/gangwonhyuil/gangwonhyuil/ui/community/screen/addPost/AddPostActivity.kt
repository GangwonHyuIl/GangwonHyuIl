package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPost

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityAddPostBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace.AddPlaceActivity
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

const val EXTRA_PLACE_RESULT = "extra_place_result"

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

    private val addPlaceActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.getStringExtra(EXTRA_PLACE_RESULT)?.let {
                    Timber.d("place result: $it")
                    Gson().fromJson(it, AddPostItem.Place::class.java)?.let { newPlace ->
                        viewModel.onAddPlace(newPlace)
                    }
                }
            }
        }

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
                    R.id.add_post_register -> {
                        when (val addPostStart = viewModel.addPostState.value) {
                            is AddPostState.Idle -> {
                                viewModel.registerPost()
                            }

                            is AddPostState.CannotRegister -> {
                                Toast
                                    .makeText(
                                        this@AddPostActivity,
                                        addPostStart.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }

                            else -> {} // do nothing
                        }
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
            lifecycleScope.launch {
                addPostState.collect {
                    when (it) {
                        is AddPostState.AddPostSuccess -> {
                            Toast
                                .makeText(this@AddPostActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }

                        is AddPostState.AddPostFailure -> {
                            Toast
                                .makeText(this@AddPostActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {} // do nothing
                    }
                    viewModel.refreshState()
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
        showDeletePlaceListAlert(placeListId)
    }

    private fun showDeletePlaceListAlert(placeListId: String) {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("장소 목록을 삭제하시겠습니까?")
            .setMessage("장소 목록에 추가된 장소들도 모두 삭제됩니다.")
            .setPositiveButton(
                "삭제"
            ) { _, _ ->
                viewModel.onDeletePlaceList(placeListId)
                showDeletePlaceListSnackBar(placeListId)
            }.setNegativeButton(
                "취소"
            ) { _, _ -> } // do nothing

        builder.show()
    }

    private fun showDeletePlaceListSnackBar(placeListId: String) {
        Snackbar
            .make(
                binding.root,
                "장소 목록이 삭제되었습니다.",
                Snackbar.LENGTH_LONG
            ).setAction("삭제 취소") {
                viewModel.rollbackDeletePlaceList()
            }.show()
    }

    override fun onAddPlaceClick(placeListId: String) {
        addPlaceActivityLauncher.launch(
            AddPlaceActivity.getAddPlaceActivityIntent(
                context = this,
                placeListId = placeListId
            )
        )
    }

    override fun onDeletePlaceClick(placeId: String) {
        viewModel.onDeletePlace(placeId)
        showDeletePlaceSnackBar(placeId)
    }

    private fun showDeletePlaceSnackBar(placeId: String) {
        Snackbar
            .make(
                binding.root,
                "장소가 삭제되었습니다.",
                Snackbar.LENGTH_LONG
            ).setAction("삭제 취소") {
                viewModel.rollbackDeletePlace()
            }.show()
    }

    override fun onAddPlaceListClick() {
        viewModel.onAddPlaceList()
    }

    companion object {
        fun getAddPostActivityIntent(context: Context): Intent = Intent(context, AddPostActivity::class.java)
    }
}