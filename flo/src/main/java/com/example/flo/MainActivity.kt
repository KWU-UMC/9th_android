package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
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
import com.example.flo.SongDetailActivity
import com.example.flo.databinding.ActivityMainBinding
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
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // main activity ↔ song activity
        getResultFromSongActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
                val songActivityAlbumTitle = result.data?.getStringExtra(INTENT_KEY)
                Toast.makeText(this, "앨범 제목: $songActivityAlbumTitle", Toast.LENGTH_SHORT).show()
            }
        }

        initListeners()
    }

    private fun initListeners() = with(binding) {
        musicPlayerContainer.setOnClickListener {
            val intent = Intent(this@MainActivity, SongDetailActivity::class.java).apply {
                putExtra(TITLE, tvTrackTitle.text.toString())
                putExtra(SINGER, tvTrackArtist.text.toString())
            }
            getResultFromSongActivity.launch(intent)
        }
        ivMusicPlay.setOnClickListener {
            when(musicState) {
                MusicState.RELEASE -> {
                    try {
                        mediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.sample).apply {
                            setOnCompletionListener {
                                mediaPlayer?.release()
                                mediaPlayer = null
                                job?.cancel()
                                musicState = MusicState.RELEASE
                                seekbarMainMusic.progress = 0
                                ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_play_filled))
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
                        ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_pause))
                    } catch (e: IOException) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                MusicState.PLAYING -> {
                    mediaPlayer?.pause()
                    job?.cancel()
                    musicState = MusicState.PAUSE
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_play_filled))
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
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_pause))
                }
            }
        }
    }

    companion object {
        const val INTENT_KEY = "key"
        const val TITLE = "title"
        const val SINGER = "singer"
    }
}