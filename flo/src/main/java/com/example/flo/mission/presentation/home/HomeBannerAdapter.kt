package com.example.flo.mission.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.home.HomeBannerOneFragment
import com.example.flo.home.HomeBannerThreeFragment
import com.example.flo.home.HomeBannerTwoFragment

class HomeBannerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeBannerOneFragment()
            1 -> HomeBannerTwoFragment()
            2 -> HomeBannerThreeFragment()
            else -> HomeBannerOneFragment()
        }
    }
    override fun getItemCount(): Int {
        return 3
    }
}