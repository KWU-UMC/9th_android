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
import kotlinx.coroutines.withContext

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnAddSong.setOnClickListener {
            // TODO: 노래 추가하기
        }
        btnAddAlbum.setOnClickListener {
            // TODO: 앨범 추가하기
        }
    }
}