package com.gangwonhyuil.gangwonhyuil.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gangwonhyuil.gangwonhyuil.ui.home.office.PlaceInfoFragment

class HomePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceInfoFragment.newInstance("공유 오피스")
            1 -> PlaceInfoFragment.newInstance("숙소")
            2 -> PlaceInfoFragment.newInstance("식당")
            3 -> PlaceInfoFragment.newInstance("카페")
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
