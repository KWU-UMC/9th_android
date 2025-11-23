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
            val musicDatabase = MusicDatabase.getInstance(context = requireContext())
            val songDao = musicDatabase.songDao

            CoroutineScope(Dispatchers.IO).launch {
                val songList = songDao.getAllSongs()
                if(songList.isEmpty()) {
                    songDao.insertSong(song = SongEntity(
                        title = "Blank Space",
                        artist = "Taylor Swift"
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Shake It Off",
                        artist = "Taylor Swift"
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Style",
                        artist = "Taylor Swift"
                    ))

                    songDao.insertSong(song = SongEntity(
                        title = "Rare",
                        artist = "Selena Gomez"
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Lose You To Love Me",
                        artist = "Selena Gomez",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Boyfriend",
                        artist = "Selena Gomez"
                    ))

                    songDao.insertSong(song = SongEntity(
                        title = "Attention",
                        artist = "Charlie Puth",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "How Long",
                        artist = "Charlie Puth",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Done for Me",
                        artist = "Charlie Puth",
                    ))

                    songDao.insertSong(song = SongEntity(
                        title = "Shape of You",
                        artist = "Ed Sheeran",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Castle on the Hill",
                        artist = "Ed Sheeran",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Perfect",
                        artist = "Ed Sheeran",
                    ))

                    songDao.insertSong(song = SongEntity(
                        title = "Teenage Dream",
                        artist = "Katy Perry",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Firework",
                        artist = "Katy Perry",
                    ))
                    songDao.insertSong(song = SongEntity(
                        title = "Last Friday Night",
                        artist = "Katy Perry",
                    ))
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "이미 곡 데이터가 존재합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnAddAlbum.setOnClickListener {
            // db에 홈 화면을 위한 앨범 정보 넣기
            val musicDatabase = MusicDatabase.getInstance(context = requireContext())
            val albumDao = musicDatabase.albumDao
            val songDao = musicDatabase.songDao
            val rawAlbumList = listOf(
                AlbumEntity(title = "1989", artist = "Taylor Swift", image = R.drawable.img_taylor_swift_album),
                AlbumEntity(title = "Rare", artist = "Selena Gomez", image = R.drawable.img_selena_gomez_album),
                AlbumEntity(title = "Voicenotes", artist = "Charlie Puth", image = R.drawable.img_charlie_puth_album),
                AlbumEntity(title = "÷ (Divide)", artist = "Ed Sheeran", image = R.drawable.img_ed_sheeran_album),
                AlbumEntity(title = "Teenage Dream", artist = "Katy Perry", image = R.drawable.img_katy_perry_album),
            )
            CoroutineScope(Dispatchers.IO).launch {
                val albumList = albumDao.getAllAlbums()
                if(albumList.isEmpty()) {
                    for(album in rawAlbumList) {
                        val updateSongList = mutableListOf<SongEntity>()
                        val songs = songDao.getSongsByArtist(artist = album.artist)
                        val album = AlbumEntity(
                            title = album.title,
                            artist = album.artist,
                            songs = songs,
                            image = album.image
                        )

                        // 삽입
                        val albumId = albumDao.insertAlbum(album = album)

                        // 업데이트
                        for(song in songs) {
                            val updatedSong = song.copy(albumIdx = albumId.toInt())
                            songDao.updateSong(song = updatedSong)
                            updateSongList.add(updatedSong)
                        }

                        albumDao.updateAlbum(album = album.copy(id = albumId.toInt(), songs = updateSongList))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "이미 앨범 데이터가 존재합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}