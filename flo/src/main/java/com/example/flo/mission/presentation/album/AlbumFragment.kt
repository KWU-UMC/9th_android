package com.example.flo.mission.presentation.album

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.mission.database.database.MusicDatabase
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.presentation.MusicViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.getValue

class AlbumFragment : Fragment(R.layout.fragment_album) {

    private lateinit var binding: FragmentAlbumBinding
    private val musicViewModel: MusicViewModel by activityViewModels()
    private val album by lazy {
        val args: AlbumFragmentArgs by navArgs()
        args.album ?: AlbumEntity(title = "기본 제목", artist = "기본 가수", image = R.drawable.img_no_track)
    }
    private val albumDao by lazy {
        MusicDatabase.getInstance(context = requireContext()).albumDao
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumBinding.bind(view)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {

        tvAlbumSongTitle.text = album.title
        tvAlbumSongSinger.text = album.artist
        ivAlbumThumbnail.setImageResource(album.image ?: R.drawable.img_no_track)

        val albumHeart = if(album.isLike) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined
        ivAlbumHeart.setImageDrawable(ContextCompat.getDrawable(requireContext(), albumHeart))

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

    private fun initListeners() = with(binding) {
        ivAlbumBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivAlbumHeart.setOnClickListener {
            if(album.isLike) {
                CoroutineScope(Dispatchers.IO).launch {
                    albumDao.updateAlbum(album = album.copy(isLike = false))
                    withContext(Dispatchers.Main) {
                        musicViewModel.updateAlbum(oldAlbum = album, newAlbum = album.copy(isLike = false))
                        ivAlbumHeart.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_outlined))
                        Toast.makeText(context, "보관함에 삭제되었습니다!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    albumDao.updateAlbum(album = album.copy(isLike = true))
                    withContext(Dispatchers.Main) {
                        musicViewModel.updateAlbum(oldAlbum = album, newAlbum = album.copy(isLike = true))
                        ivAlbumHeart.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_filled))
                        Toast.makeText(context, "보관함에 저장되었습니다!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun changeThumbnailImage(image: Int) {
        binding.ivAlbumThumbnail.setImageDrawable(ContextCompat.getDrawable(requireContext(), image))
    }

}