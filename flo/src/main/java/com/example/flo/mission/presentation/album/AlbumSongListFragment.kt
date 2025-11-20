package com.example.flo.mission.presentation.album

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumListBinding
import com.example.flo.mission.model.data.SongEntity

class AlbumSongListFragment(private val songs: List<SongEntity>?) : Fragment(R.layout.fragment_album_list) {

    private lateinit var binding: FragmentAlbumListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumListBinding.bind(view)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val albumSongListAdapter = AlbumSongListAdapter(songs = songs ?: emptyList())
        recyclerviewAlbumList.adapter = albumSongListAdapter
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