package com.example.flo.mission.presentation.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import android.util.Log
import com.example.flo.databinding.FragmentArchiveSaveAlbumBinding
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.model.AlbumModel
import com.example.flo.mission.presentation.MusicViewModel

class ArchiveSaveAlbumFragment : Fragment(R.layout.fragment_archive_save_album), OnItemDeleteListener {

    private lateinit var binding: FragmentArchiveSaveAlbumBinding
    private val musicViewModel: MusicViewModel by activityViewModels()
    private lateinit var adapter: ArchiveSaveAlbumAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveSaveAlbumBinding.bind(view)
        initObservers()
    }

    private fun initObservers() = with(binding) {
        musicViewModel.albumResources.observe(viewLifecycleOwner) { albums ->
            val savedAlbums = mutableListOf<AlbumEntity>()
            albums.forEach { album ->
                if(album.isLike) savedAlbums.add(album)
            }
            adapter = ArchiveSaveAlbumAdapter(items = savedAlbums, context = requireContext())
            adapter.onItemDeleteListener = this@ArchiveSaveAlbumFragment
            recyclerviewArchiveSaveAlbum.adapter = adapter
        }
    }

    override fun onDeleteIconClick(album: AlbumEntity) {
        musicViewModel.deleteAlbum(album = album)
    }

}