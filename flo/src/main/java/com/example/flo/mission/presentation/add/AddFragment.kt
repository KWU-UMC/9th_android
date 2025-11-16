package com.example.flo.mission.presentation.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentAddBinding
import com.example.flo.mission.database.database.MusicDatabase
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.database.entity.SongEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding
    private val musicDatabase by lazy {
        MusicDatabase.getInstance(context = requireContext())
    }
    private val songDao by lazy {
        musicDatabase.songDao()
    }
    private val albumDao by lazy {
        musicDatabase.albumDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnAddSong.setOnClickListener {
            if(etSongTitle.text.isBlank() || etSongArtist.text.isBlank()) {
                Toast.makeText(context, "노래 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    songDao.insertSong(SongEntity(
                        title = etSongTitle.text.toString(),
                        artist = etSongArtist.text.toString()
                    ))
                }
            }
        }
        btnAddAlbum.setOnClickListener {
            if(etAlbumTitle.text.isBlank() || etAlbumArtist.text.isBlank()) {
                Toast.makeText(context, "앨범 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    albumDao.insertAlbum(album = AlbumEntity(
                        title = etAlbumTitle.text.toString(),
                        artist = etAlbumArtist.text.toString()
                    ))
                }
            }
        }
    }

}