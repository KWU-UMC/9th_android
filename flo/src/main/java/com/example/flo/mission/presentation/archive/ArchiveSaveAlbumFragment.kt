package com.example.flo.mission.presentation.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.databinding.FragmentArchiveSaveAlbumBinding
import com.example.flo.mission.model.AlbumModel

class ArchiveSaveAlbumFragment : Fragment(R.layout.fragment_archive_save_album) {

    private lateinit var binding: FragmentArchiveSaveAlbumBinding
    private val dummyItems = listOf(
        AlbumModel(
            albumId = 1,
            albumTitle = "City Pop Revival",
            albumThumbnail = R.drawable.img_track_sample_one, // 더미 리소스 ID
            albumArtistName = "Neon Wave",
            albumReleaseDate = "2024.10.01",
            albumFormat = "정규",
            albumGenre = "JPOP"
        ),
        AlbumModel(
            albumId = 2,
            albumTitle = "Midnight Drive",
            albumThumbnail = R.drawable.img_track_sample_two,
            albumArtistName = "Stella",
            albumReleaseDate = "2023.05.15",
            albumFormat = "EP",
            albumGenre = "POP"
        ),
        AlbumModel(
            albumId = 3,
            albumTitle = "사랑의 방정식",
            albumThumbnail = R.drawable.img_track_sample_three,
            albumArtistName = "이서준",
            albumReleaseDate = "2025.01.20",
            albumFormat = "싱글",
            albumGenre = "K-POP"
        ),
        AlbumModel(
            albumId = 4,
            albumTitle = "The Winter's Tale",
            albumThumbnail = R.drawable.img_track_sample_four,
            albumArtistName = "The Quiet",
            albumReleaseDate = "2022.12.01",
            albumFormat = "베스트",
            albumGenre = "Ballad"
        ),
        AlbumModel(
            albumId = 5,
            albumTitle = "Digital Dream",
            albumThumbnail = R.drawable.img_track_sample_five,
            albumArtistName = "A.I. Sounds",
            albumReleaseDate = "2024.08.25",
            albumFormat = "정규",
            albumGenre = "Electronic"
        ),
        AlbumModel(
            albumId = 6,
            albumTitle = "길 위에서",
            albumThumbnail = R.drawable.img_track_sample_six,
            albumArtistName = "여행자",
            albumReleaseDate = "2023.11.11",
            albumFormat = "정규",
            albumGenre = "Folk"
        ),
        AlbumModel(
            albumId = 7,
            albumTitle = "Crimson Moon",
            albumThumbnail = R.drawable.img_track_sample_one,
            albumArtistName = "Blackout",
            albumReleaseDate = "2025.02.28",
            albumFormat = "EP",
            albumGenre = "Rock"
        ),
        AlbumModel(
            albumId = 8,
            albumTitle = "Sunshine & Rain",
            albumThumbnail = R.drawable.img_track_sample_two,
            albumArtistName = "Summer Breeze",
            albumReleaseDate = "2024.06.07",
            albumFormat = "싱글",
            albumGenre = "Jazz"
        ),
        AlbumModel(
            albumId = 9,
            albumTitle = "미래 도시",
            albumThumbnail = R.drawable.img_track_sample_three,
            albumArtistName = "CYBER",
            albumReleaseDate = "2023.09.13",
            albumFormat = "정규",
            albumGenre = "K-POP"
        ),
        AlbumModel(
            albumId = 10,
            albumTitle = "Hidden Track",
            albumThumbnail = R.drawable.img_track_sample_four,
            albumArtistName = "Unknown",
            albumReleaseDate = "2025.04.01",
            albumFormat = "미니",
            albumGenre = "POP"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveSaveAlbumBinding.bind(view)
        initViews()
    }

    private fun initViews() = with(binding) {
        recyclerviewArchiveSaveAlbum.adapter = ArchiveSaveAlbumAdapter(items = dummyItems, context = requireContext())
        recyclerviewArchiveSaveAlbum.layoutManager = LinearLayoutManager(context)
    }

}