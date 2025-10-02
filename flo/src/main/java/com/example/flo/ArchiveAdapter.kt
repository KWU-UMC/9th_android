package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArchiveAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SaveSongFragment()
            1 -> SongFileFragment()
            2 -> SaveAlbumFragment()
            else -> SaveSongFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}