package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlaceDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.gangwonhyuil.gangwonhyuil.databinding.ActivitySearchPlaceDetailBinding
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity

const val EXTRA_PLACE_NAME = "extra_place_name"
const val EXTRA_PLACE_URL = "extra_place_url"

class SearchPlaceDetailActivity : BaseActivity<ActivitySearchPlaceDetailBinding>() {
    override fun inflateBinding(inflater: LayoutInflater) = ActivitySearchPlaceDetailBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setBackPressedDispatcher()
    }

    private fun initView() {
        initTopAppBar()
        initWebView()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            title = intent.getStringExtra(EXTRA_PLACE_NAME)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(binding.wvPlaceDetail) {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            settings.apply {
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(false)
                builtInZoomControls = false
                supportMultipleWindows()
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                domStorageEnabled = true
            }
            val url = intent.getStringExtra(EXTRA_PLACE_URL)
            loadUrl(url!!)
        }
    }

    private fun setBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            owner = this,
            onBackPressedCallback =
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (binding.wvPlaceDetail.canGoBack()) {
                            binding.wvPlaceDetail.goBack()
                        } else {
                            finish()
                        }
                    }
                }
        )
    }

    companion object {
        fun getPlaceWebViewActivityIntent(
            context: Context,
            placeName: String,
            placeUrl: String,
        ): Intent =
            Intent(context, SearchPlaceDetailActivity::class.java).apply {
                putExtra(EXTRA_PLACE_NAME, placeName)
                putExtra(EXTRA_PLACE_URL, placeUrl)
            }
    }
}