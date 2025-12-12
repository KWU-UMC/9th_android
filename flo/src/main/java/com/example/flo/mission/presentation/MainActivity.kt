package com.example.flo.mission.presentation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.flo.R
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.mission.domain.model.MusicState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var getResultFromSongActivity: ActivityResultLauncher<Intent>

    private var mediaPlayer: MediaPlayer? = null
    private var musicState: MusicState = MusicState.RELEASE
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        Thread.sleep(3000)
        super.onCreate(savedInstanceState)
        Log.e("ACTIVITY A", "onCreate")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        // main activity ↔ song activity
        getResultFromSongActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val songActivityAlbumTitle = result.data?.getStringExtra(INTENT_KEY)
                    Toast.makeText(
                        this@MainActivity,
                        "앨범 제목: $songActivityAlbumTitle",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        Log.e("ACTIVITY A", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("ACTIVITY A", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("ACTIVITY A", "onPause")
        mediaPlayer?.pause()
        job?.cancel()
        musicState = MusicState.PAUSE
    }

    override fun onStop() {
        super.onStop()
        Log.e("ACTIVITY A", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("ACTIVITY A", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ACTIVITY A", "onDestroy")
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initListeners() = with(binding) {
        ivMusicPlayList.setOnClickListener {
            val intent = Intent(this@MainActivity, SongDetailActivity::class.java).apply {
                putExtra(TITLE, tvTrackTitle.text.toString())
                putExtra(SINGER, tvTrackArtist.text.toString())
                putExtra(CURRENT_MUSIC_POSITION, mediaPlayer?.currentPosition)
                putExtra(CURRENT_PLAY_STATE, musicState.name)
            }
            getResultFromSongActivity.launch(intent)
        }
        ivMusicPlay.setOnClickListener {
            when (musicState) {
                MusicState.RELEASE -> {
                    try {
                        mediaPlayer =
                            MediaPlayer.create(this@MainActivity, R.raw.sample).apply {
                                setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    job?.cancel()
                                    musicState = MusicState.RELEASE
                                    seekbarMainMusic.progress = 0
                                    ivMusicPlay.setImageDrawable(
                                        ContextCompat.getDrawable(
                                            this@MainActivity,
                                            R.drawable.ic_play_filled
                                        )
                                    )
                                }
                            }
                        mediaPlayer?.start()
                        job = lifecycleScope.launch {
                            while (isActive) {
                                seekbarMainMusic.progress = mediaPlayer?.currentPosition ?: 0
                                delay(100L)
                            }
                        }
                        musicState = MusicState.PLAYING
                        seekbarMainMusic.max = mediaPlayer?.duration ?: 0
                        ivMusicPlay.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_pause
                            )
                        )
                    } catch (e: IOException) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

                MusicState.PLAYING -> {
                    mediaPlayer?.pause()
                    job?.cancel()
                    musicState = MusicState.PAUSE
                    ivMusicPlay.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_play_filled
                        )
                    )
                }

                MusicState.PAUSE -> {
                    mediaPlayer?.start()
                    job = lifecycleScope.launch {
                        while (isActive) {
                            seekbarMainMusic.progress = mediaPlayer?.currentPosition ?: 0
                            delay(100L)
                        }
                    }
                    musicState = MusicState.PLAYING
                    ivMusicPlay.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity,
                            R.drawable.ic_pause
                        )
                    )
                }
            }
        }
    }

    companion object {
        const val INTENT_KEY = "KEY"
        const val TITLE = "TITLE"
        const val SINGER = "SINGER"
        const val CURRENT_MUSIC_POSITION = "CURRENT_MUSIC_POSITION"
        const val CURRENT_PLAY_STATE = "CURRENT_PLAY_STATE"
    }
}