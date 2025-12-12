package com.example.flo.mission.presentation.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.mission.data.local.room.entity.SongEntity

class AlbumAdapter(fragment: Fragment, private val songs: List<SongEntity>?): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AlbumSongListFragment(songs = songs)
            1 -> AlbumDetailFragment()
            2 -> AlbumVideoFragment()
            else -> AlbumSongListFragment(songs = songs)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}