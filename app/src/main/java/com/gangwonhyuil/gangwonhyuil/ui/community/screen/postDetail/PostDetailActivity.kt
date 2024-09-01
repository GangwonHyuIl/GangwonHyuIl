package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.view.LayoutInflater
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityPostDetailBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {
    override fun inflateBinding(inflater: LayoutInflater): ActivityPostDetailBinding =
        ActivityPostDetailBinding.inflate(layoutInflater)
}