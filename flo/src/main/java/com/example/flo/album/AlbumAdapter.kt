package com.example.flo.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AlbumListFragment()
            1 -> AlbumDetailFragment()
            2 -> AlbumVideoFragment()
            else -> AlbumListFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}