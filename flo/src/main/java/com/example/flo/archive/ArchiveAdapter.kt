package com.example.flo.archive

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArchiveAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ArchiveSaveTrackFragment()
            1 -> ArchiveTrackFileFragment()
            2 -> ArchiveSaveAlbumFragment()
            else -> ArchiveSaveTrackFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}