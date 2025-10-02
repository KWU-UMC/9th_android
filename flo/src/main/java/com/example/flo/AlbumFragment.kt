package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment(R.layout.fragment_album) {

    private lateinit var binding: FragmentAlbumBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumBinding.bind(view)
        initViews()
    }

    private fun initViews() = with(binding) {
        val args: AlbumFragmentArgs by navArgs()
        val albumData = args.albumData
        tvAlbumSongTitle.text = albumData.title
        tvAlbumSongSinger.text = albumData.singer
        ivAlbumThumbnail.setImageResource(albumData.thumbnail)

        albumViewPager2.adapter = AlbumAdapter(this@AlbumFragment)
        TabLayoutMediator(tblAlbumIndicator, albumViewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> "수록곡"
                1 -> "상세정보"
                2 -> "영상"
                else -> ""
            }
        }.attach()
    }

}