package com.example.flo.album

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flo.R
import com.example.flo.TrackModel
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
        val albumData = args.track ?: TrackModel(trackTitle = "기본 제목", trackArtist = "기본 가수", trackThumbnail = R.drawable.img_no_track)

        tvAlbumSongTitle.text = albumData.trackTitle
        tvAlbumSongSinger.text = albumData.trackArtist
        ivAlbumThumbnail.setImageResource(albumData.trackThumbnail ?: R.drawable.img_no_track)

        albumViewPager2.adapter = AlbumAdapter(this@AlbumFragment)
        TabLayoutMediator(tblAlbumIndicator, albumViewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "수록곡"
                1 -> "상세정보"
                2 -> "영상"
                else -> ""
            }
        }.attach()
    }

    fun changeThumbnailImage(image: Int) {
        binding.ivAlbumThumbnail.setImageDrawable(ContextCompat.getDrawable(requireContext(), image))
    }

}