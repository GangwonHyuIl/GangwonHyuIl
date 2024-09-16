package com.gangwonhyuil.gangwonhyuil.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gangwonhyuil.gangwonhyuil.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView() {
        binding.tvBack.setOnClickListener {
            finish()
        }
    }
}