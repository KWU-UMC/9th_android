package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
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