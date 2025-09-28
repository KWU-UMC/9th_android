package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flo.databinding.FragmentAlbumBinding

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
    }

}