package com.example.flo.album

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.TrackModel
import com.example.flo.databinding.FragmentAlbumListBinding

class AlbumListFragment : Fragment(R.layout.fragment_album_list) {

    private lateinit var binding: FragmentAlbumListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumListBinding.bind(view)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val testAlbumList = mutableListOf(
            TrackModel(
                trackId = 1,
                trackTitle = "Watermelon Sugar",
                trackArtist = "Harry Styles"
            ),
            TrackModel(
                trackId = 2,
                trackTitle = "Blinding Lights",
                trackArtist = "The Weeknd"
            ),
            TrackModel(
                trackId = 3,
                trackTitle = "Levitating",
                trackArtist = "Dua Lipa"
            ),
            TrackModel(
                trackId = 4,
                trackTitle = "As It Was",
                trackArtist = "Harry Styles"
            ),
            TrackModel(
                trackId = 5,
                trackTitle = "Stay",
                trackArtist = "The Kid LAROI & Justin Bieber"
            ),
            TrackModel(
                trackId = 6,
                trackTitle = "Bad Guy",
                trackArtist = "Billie Eilish"
            ),
            TrackModel(
                trackId = 7,
                trackTitle = "Happier Than Ever",
                trackArtist = "Billie Eilish"
            ),
            TrackModel(
                trackId = 8,
                trackTitle = "Midnight Sky",
                trackArtist = "Miley Cyrus"
            ),
            TrackModel(
                trackId = 9,
                trackTitle = "Kiss Me More",
                trackArtist = "Doja Cat"
            ),
            TrackModel(
                trackId = 10,
                trackTitle = "Good 4 u",
                trackArtist = "Olivia Rodrigo"
            )
        )

        val albumListAdapter = AlbumListAdapter(testAlbumList)
        recyclerviewAlbumList.adapter = albumListAdapter
        recyclerviewAlbumList.layoutManager = LinearLayoutManager(context)
    }

    private fun initListeners() = with(binding) {
        switchAlbumList.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    Toast.makeText(context, "내 취향 MIX 모드가 켜졌습니다.", Toast.LENGTH_SHORT).show()
                    val parentFragment = parentFragment as AlbumFragment
                    parentFragment.changeThumbnailImage(R.drawable.img_home_banner_two_album_one)
                } else {
                    Toast.makeText(context, "내 취향 MIX 모드가 꺼졌습니다.", Toast.LENGTH_SHORT).show()
                    val parentFragment = parentFragment as AlbumFragment
                    parentFragment.changeThumbnailImage(R.drawable.img_album_today_released_1)
                }
            }
        })
    }

}