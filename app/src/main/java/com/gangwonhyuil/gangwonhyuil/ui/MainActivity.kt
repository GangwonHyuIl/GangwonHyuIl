package com.gangwonhyuil.gangwonhyuil.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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


//        바텀 네비
        supportFragmentManager.beginTransaction().add(binding.navHostFragment.id, HomeFragment())
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
//                    navController.navigate(R.id.navigation_home)
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.navigation_ai -> {
                    replaceFragment(AiFragment())
                    true
                }

                R.id.navigation_community -> {
                    replaceFragment(CommunityFragment())
                    true
                }

                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.navHostFragment.id, fragment)
            .commit()
    }

}