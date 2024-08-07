package com.gangwonhyuil.gangwonhyuil.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView() {
        binding.tvBack.setOnClickListener {
            finish()
        }
    }
}