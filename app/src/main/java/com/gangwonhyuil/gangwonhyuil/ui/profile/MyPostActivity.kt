package com.gangwonhyuil.gangwonhyuil.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMyPostBinding

class MyPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView() {
        binding.tvBack.setOnClickListener {
            finish()
        }
    }
}