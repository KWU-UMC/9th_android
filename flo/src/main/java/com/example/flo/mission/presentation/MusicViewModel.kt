package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flo.R
import com.example.flo.mission.database.dao.AlbumDao
import com.example.flo.mission.database.dao.SongDao
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.database.entity.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicViewModel(private val albumDao: AlbumDao, private val songDao: SongDao) : ViewModel() {

    // viewmodel 인스턴스가 메모리에 생성될 때 가장 먼저 실행된다. fragment/activity가 by viewModels()를 통해 요청하는 순간 실행된다.
    init {
        viewModelScope.launch(Dispatchers.IO) {
            initializeSong()
            initializeAlbum()
        }
    }

    private val rawAlbumList = listOf(
        AlbumEntity(title = "1989", artist = "Taylor Swift", image = R.drawable.img_taylor_swift_album, releaseDate = "2014.10.27", format = "정규", genre = "Synth-pop"),
        AlbumEntity(title = "Rare", artist = "Selena Gomez", image = R.drawable.img_selena_gomez_album, releaseDate = "2020.01.10", format = "정규", genre = "Dance-pop"),
        AlbumEntity(title = "Voicenotes", artist = "Charlie Puth", image = R.drawable.img_charlie_puth_album, releaseDate = "2018.05.11", format = "정규", genre = "R&B"),
        AlbumEntity(title = "÷ (Divide)", artist = "Ed Sheeran", image = R.drawable.img_ed_sheeran_album, releaseDate = "2017.03.03", format = "정규", genre = "Folk Pop"),
        AlbumEntity(title = "Teenage Dream", artist = "Katy Perry", image = R.drawable.img_katy_perry_album, releaseDate = "2010.08.24", format = "정규", genre = "Electropop"),
    )

    private val _songResources: MutableLiveData<List<SongEntity>> = MutableLiveData()
    val songResources: LiveData<List<SongEntity>> get() = _songResources

    fun setSongResources(songResources: List<SongEntity>) {
        _songResources.value = songResources
    }

    private val _albumResources: MutableLiveData<List<AlbumEntity>> = MutableLiveData()
    val albumResources: LiveData<List<AlbumEntity>> get() = _albumResources

    private fun initializeSong() {
        val songList = songDao.getAllSongs()
        if (songList.isEmpty()) {
            songDao.insertSong(
                song = SongEntity(
                    title = "Blank Space",
                    artist = "Taylor Swift"
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Shake It Off",
                    artist = "Taylor Swift"
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Style",
                    artist = "Taylor Swift"
                )
            )

            songDao.insertSong(
                song = SongEntity(
                    title = "Rare",
                    artist = "Selena Gomez"
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Lose You To Love Me",
                    artist = "Selena Gomez",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Boyfriend",
                    artist = "Selena Gomez"
                )
            )

            songDao.insertSong(
                song = SongEntity(
                    title = "Attention",
                    artist = "Charlie Puth",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "How Long",
                    artist = "Charlie Puth",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Done for Me",
                    artist = "Charlie Puth",
                )
            )

            songDao.insertSong(
                song = SongEntity(
                    title = "Shape of You",
                    artist = "Ed Sheeran",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Castle on the Hill",
                    artist = "Ed Sheeran",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Perfect",
                    artist = "Ed Sheeran",
                )
            )

            songDao.insertSong(
                song = SongEntity(
                    title = "Teenage Dream",
                    artist = "Katy Perry",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Firework",
                    artist = "Katy Perry",
                )
            )
            songDao.insertSong(
                song = SongEntity(
                    title = "Last Friday Night",
                    artist = "Katy Perry",
                )
            )
        }
    }

    private suspend fun initializeAlbum() {
        val albumList = albumDao.getAllAlbums()
        if (albumList.isEmpty()) {
            for (rawAlbum in rawAlbumList) {
                val updateSongList = mutableListOf<SongEntity>()
                val songs = songDao.getSongsByArtist(artist = rawAlbum.artist)
                val processedAlbum = rawAlbum.copy(songs = songs)

                // 삽입
                val albumId = albumDao.insertAlbum(album = processedAlbum)

                // 업데이트
                for (song in songs) {
                    val updatedSong = song.copy(albumIdx = albumId.toInt())
                    songDao.updateSong(song = updatedSong)
                    updateSongList.add(updatedSong)
                }

                albumDao.updateAlbum(
                    album = processedAlbum.copy(
                        id = albumId.toInt(),
                        songs = updateSongList
                    )
                )
            }
            withContext(Dispatchers.Main) {
                _albumResources.value = albumDao.getAllAlbums()
            }
        } else {
            withContext(Dispatchers.Main) {
                _albumResources.value = albumList
            }
        }
    }

    fun setAlbumResources(albumResources: List<AlbumEntity>) {
        _albumResources.value = albumResources
    }

    fun updateAlbumResources(oldAlbum: AlbumEntity, newAlbum: AlbumEntity) {
        val albumResources = _albumResources.value.toMutableList()
        val updateIndex = albumResources.indexOf(oldAlbum)
        albumResources[updateIndex] = newAlbum
        _albumResources.value = albumResources
    }
}