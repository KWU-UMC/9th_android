package com.example.flo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumDetailBinding

class AlbumDetailFragment : Fragment(R.layout.fragment_album_detail) {

    private lateinit var binding: FragmentAlbumDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() = with(binding) {
        val parentFragment = parentFragment as AlbumFragment
        val albumName = parentFragment.view?.findViewById<TextView>(R.id.tvAlbumSongTitle)?.text.toString()
        val albumSinger = parentFragment.view?.findViewById<TextView>(R.id.tvAlbumSongSinger)?.text.toString()
        this.albumName = albumName
        this.albumSinger = albumSinger
    }

}