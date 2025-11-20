package com.example.flo.mission.presentation.album

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.mission.model.data.AlbumEntity
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
        val album = args.album ?: AlbumEntity(title = "기본 제목", artist = "기본 가수", image = R.drawable.img_no_track)

        tvAlbumSongTitle.text = album.title
        tvAlbumSongSinger.text = album.artist
        ivAlbumThumbnail.setImageResource(album.image ?: R.drawable.img_no_track)

        albumViewPager2.adapter = AlbumAdapter(fragment = this@AlbumFragment, songs = album.songs)
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