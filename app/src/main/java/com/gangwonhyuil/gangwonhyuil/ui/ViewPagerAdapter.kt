package com.gangwonhyuil.gangwonhyuil.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gangwonhyuil.gangwonhyuil.R

class ViewPagerAdapter(fragmentList: FragmentActivity) : FragmentStateAdapter(fragmentList) {
    var item = listOf<Fragment>()

    override fun getItemCount(): Int {
        return item.size
    }

    override fun createFragment(position: Int): Fragment {
        return item.get(position)
    }
}