package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.databinding.ActivitySongDetailBinding
import java.io.IOException

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding
    private var isShuffleOn: Boolean = false
    private var repeatState: MusicRepeatState = MusicRepeatState.NONE

    private var mediaPlayer: MediaPlayer? = null
    private var musicState: MusicState = MusicState.RELEASE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extraPadding = resources.getDimensionPixelSize(R.dimen.activity_default_padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + extraPadding,
                systemBars.top + extraPadding,
                systemBars.right + extraPadding,
                systemBars.bottom + extraPadding
            )
            insets
        }
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val musicTitle = intent.getStringExtra(MainActivity.TITLE)
        val musicSinger = intent.getStringExtra(MainActivity.SINGER)
        ivDetailMusicTitle.text = musicTitle
        ivDetailMusicSinger.text = musicSinger
    }

    private fun initListeners() = with(binding) {
        ivDetailMusicCollapse.setOnClickListener {
            val intent = Intent(this@SongDetailActivity, MainActivity::class.java).apply {
                putExtra(MainActivity.INTENT_KEY, ivDetailMusicTitle.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        ivMusicPlay.setOnClickListener {
            when(musicState) {
                MusicState.RELEASE -> {
                    mediaPlayer = MediaPlayer.create(this@SongDetailActivity, R.raw.sample).apply {
                        setOnCompletionListener {
                            mediaPlayer?.release()
                            mediaPlayer = null
                            musicState = MusicState.RELEASE
                            ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                        }
                    }
                    try {
                        mediaPlayer?.start()
                        musicState = MusicState.PLAYING
                        ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_pause))
                    } catch (e: IOException) {
                        Toast.makeText(this@SongDetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                MusicState.PLAYING -> {
                    mediaPlayer?.pause()
                    musicState = MusicState.PAUSE
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                }
                MusicState.PAUSE -> {
                    mediaPlayer?.start()
                    musicState = MusicState.PLAYING
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_pause))
                }
            }
        }
        ivMusicShuffle.setOnClickListener {
            isShuffleOn = !isShuffleOn
            val color = if (isShuffleOn) R.color.ic_state_activate else R.color.ic_state_inactivate
            val message = if(isShuffleOn) "무작위로 재생됩니다." else "순차적으로 재생됩니다."
            ivMusicShuffle.setColorFilter(ContextCompat.getColor(this@SongDetailActivity, color))
            Toast.makeText(this@SongDetailActivity, message, Toast.LENGTH_SHORT).show()
        }
        ivMusicRepeat.setOnClickListener {
            when(repeatState) {
                MusicRepeatState.NONE -> {
                    repeatState = MusicRepeatState.ALL
                    ivMusicRepeat.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_repeat_all_active))
                    Toast.makeText(this@SongDetailActivity, "전체 음악을 반복합니다.", Toast.LENGTH_SHORT).show()
                }
                MusicRepeatState.ALL -> {
                    repeatState = MusicRepeatState.ONE
                    ivMusicRepeat.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_repeat_one_inside))
                    Toast.makeText(this@SongDetailActivity, "현재 음악을 반복합니다.", Toast.LENGTH_SHORT).show()
                }
                MusicRepeatState.ONE -> {
                    repeatState = MusicRepeatState.NONE
                    ivMusicRepeat.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_repeat_all_inactive))
                    Toast.makeText(this@SongDetailActivity, "반복을 사용하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}