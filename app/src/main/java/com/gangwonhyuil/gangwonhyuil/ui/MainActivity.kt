package com.gangwonhyuil.gangwonhyuil.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMainBinding
import com.gangwonhyuil.gangwonhyuil.ui.ai.AiFragment
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.community.CommunityFragment
import com.gangwonhyuil.gangwonhyuil.ui.home.HomeFragment
import com.gangwonhyuil.gangwonhyuil.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val bottomNavigationView: BottomNavigationView by lazy { binding.bottomNavigation }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager()
        navigation()
    }

    private fun viewPager() {
        val fragmentList =
            listOf(HomeFragment(), AiFragment(), CommunityFragment(), ProfileFragment())
        val viewPager = ViewPagerAdapter(this)
        viewPager.item = fragmentList
        binding.viewPager2.adapter = viewPager

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun navigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.navigation_home -> {
                    binding.viewPager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_ai -> {
                    binding.viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_community -> {
                    binding.viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    binding.viewPager2.currentItem = 3
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding.viewPager2.currentItem = 4
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}