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
//            if(etSongTitle.text.isBlank() || etSongArtist.text.isBlank()) {
//                Toast.makeText(context, "노래 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()
//            } else {
//                CoroutineScope(Dispatchers.IO).launch {
//                    songDao.insertSong(SongEntity(
//                        title = etSongTitle.text.toString(),
//                        artist = etSongArtist.text.toString()
//                    ))
//                }
//            }
        }
        btnAddAlbum.setOnClickListener {
//            if(etAlbumTitle.text.isBlank() || etAlbumArtist.text.isBlank()) {
//                Toast.makeText(context, "앨범 정보를 먼저 입력해주세요", Toast.LENGTH_SHORT).show()
//            } else {
//                CoroutineScope(Dispatchers.IO).launch {
//                    albumDao.insertAlbum(album = AlbumEntity(
//                        title = etAlbumTitle.text.toString(),
//                        artist = etAlbumArtist.text.toString()
//                    ))
//                }
//            }

            // db에 홈 화면을 위한 앨범 정보 넣기
            val musicDatabase = MusicDatabase.getInstance(context = requireContext())
            val albumDao = musicDatabase.albumDao()
            CoroutineScope(Dispatchers.IO).launch {
                albumDao.insertAlbum(album = AlbumEntity(
                    title = "1989",
                    artist = "Taylor Swift",
                    thumbnail = R.drawable.img_taylor_swift_album,
                    tracks = listOf(
                        SongEntity(
                            title = "Blank Space",
                            artist = "Taylor Swift",
                            albumIdx = 1
                        ),
                        SongEntity(
                            title = "Shake It Off",
                            artist = "Taylor Swift",
                            albumIdx = 1
                        ),
                        SongEntity(
                            title = "Style",
                            artist = "Taylor Swift",
                            albumIdx = 1
                        )
                    )
                ))
                albumDao.insertAlbum(album = AlbumEntity(
                    title = "Rare",
                    artist = "Selena Gomez",
                    thumbnail = R.drawable.img_selena_gomez_album,
                    tracks = listOf(
                        SongEntity(
                            title = "Rare",
                            artist = "Selena Gomez",
                            albumIdx = 2
                        ),
                        SongEntity(
                            title = "Lose You To Love Me",
                            artist = "Selena Gomez",
                            albumIdx = 2
                        ),
                        SongEntity(
                            title = "Boyfriend",
                            artist = "Selena Gomez",
                            albumIdx = 2
                        )
                    )
                ))
                albumDao.insertAlbum(album = AlbumEntity(
                    title = "Voicenotes",
                    artist = "Charlie Puth",
                    thumbnail = R.drawable.img_charlie_puth_album,
                    tracks = listOf(
                        SongEntity(
                            title = "Attention",
                            artist = "Charlie Puth",
                            albumIdx = 3
                        ),
                        SongEntity(
                            title = "How Long",
                            artist = "Charlie Puth",
                            albumIdx = 3
                        ),
                        SongEntity(
                            title = "Done for Me",
                            artist = "Charlie Puth",
                            albumIdx = 3
                        )
                    )
                ))
                albumDao.insertAlbum(album = AlbumEntity(
                    title = "÷ (Divide)",
                    artist = "Ed Sheeran",
                    thumbnail = R.drawable.img_ed_sheeran_album,
                    tracks = listOf(
                        SongEntity(
                            title = "Shape of You",
                            artist = "Ed Sheeran",
                            albumIdx = 4
                        ),
                        SongEntity(
                            title = "Castle on the Hill",
                            artist = "Ed Sheeran",
                            albumIdx = 4
                        ),
                        SongEntity(
                            title = "Perfect",
                            artist = "Ed Sheeran",
                            albumIdx = 4
                        )
                    )
                ))
                albumDao.insertAlbum(album = AlbumEntity(
                    title = "Teenage Dream",
                    artist = "Katy Perry",
                    thumbnail = R.drawable.img_katy_perry_album,
                    tracks = listOf(
                        SongEntity(
                            title = "Teenage Dream",
                            artist = "Katy Perry",
                            albumIdx = 5
                        ),
                        SongEntity(
                            title = "Firework",
                            artist = "Katy Perry",
                            albumIdx = 5
                        ),
                        SongEntity(
                            title = "Last Friday Night",
                            artist = "Katy Perry",
                            albumIdx = 5
                        )
                    )
                ))
            }
        }
    }
}