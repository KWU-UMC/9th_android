package com.example.flo.mission.presentation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.flo.mission.domain.model.MusicRepeatState
import com.example.flo.mission.domain.model.MusicState
import com.example.flo.R
import com.example.flo.databinding.ActivitySongDetailBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding
    private var job: Job? = null
    private var currentPlayTime: Long = -100L // 단위: ms (기본값이 -100L인 이유 → 음악 재생 시작하자마자 100ms 더하기 때문)

    private var mediaPlayer: MediaPlayer? = null
    private var musicState: MusicState = MusicState.RELEASE

    private var isShuffleOn: Boolean = false
    private var repeatState: MusicRepeatState = MusicRepeatState.NONE

    private val currentMusicPosition by lazy {
        intent.getIntExtra(MainActivity.Companion.CURRENT_MUSIC_POSITION, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ACTIVITY B", "onCreate")
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

    override fun onStart() {
        super.onStart()
        Log.e("ACTIVITY B", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("ACTIVITY B", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("ACTIVITY B", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("ACTIVITY B", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("ACTIVITY B", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ACTIVITY B", "onDestroy")
    }

    private fun initViews() = with(binding) {
        mediaPlayer = MediaPlayer.create(this@SongDetailActivity, R.raw.sample).apply {
            setOnCompletionListener {
                mediaPlayer?.release()
                mediaPlayer = null
                job?.cancel()
                currentPlayTime = 0
                tvSongTimeStart.text = getFormattedTime(currentPlayTime.toInt())
                musicState = MusicState.RELEASE
                ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                seekbarDetailMusic.progress = 0
            }
        } // 미리 준비

        ivDetailMusicTitle.text = intent.getStringExtra(MainActivity.Companion.TITLE)
        ivDetailMusicSinger.text = intent.getStringExtra(MainActivity.Companion.SINGER)
        val state = intent.getStringExtra(MainActivity.Companion.CURRENT_PLAY_STATE)
        musicState = state?.let { MusicState.valueOf(it) } ?: MusicState.RELEASE
        when(musicState) {
            MusicState.RELEASE -> {
                ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                seekbarDetailMusic.progress = 0
            }
            MusicState.PLAYING -> {
                ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_pause))
                seekbarDetailMusic.progress = currentMusicPosition
                mediaPlayer?.seekTo(currentMusicPosition)
                mediaPlayer?.start()
                currentPlayTime = currentMusicPosition.toLong() - 100L
                job = lifecycleScope.launch {
                    while (isActive) {
                        currentPlayTime += 100L
                        tvSongTimeStart.text = getFormattedTime(currentPlayTime.toInt())
                        seekbarDetailMusic.progress = mediaPlayer?.currentPosition ?: 0
                        delay(100L)
                    }
                }
            }
            MusicState.PAUSE -> {
                ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                seekbarDetailMusic.progress = intent.getIntExtra(MainActivity.Companion.CURRENT_MUSIC_POSITION, 0)
                mediaPlayer?.seekTo(currentMusicPosition)
                currentPlayTime = currentMusicPosition.toLong() - 100L
            }
        }

        seekbarDetailMusic.max = mediaPlayer?.duration ?: 0
        tvSongTimeEnd.text = getFormattedTime(mediaPlayer?.duration ?: 0)
    }

    private fun initListeners() = with(binding) {
        ivDetailMusicCollapse.setOnClickListener {
            val intent = Intent(this@SongDetailActivity, MainActivity::class.java).apply {
                putExtra(MainActivity.Companion.INTENT_KEY, ivDetailMusicTitle.text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        ivMusicPlay.setOnClickListener {
            when(musicState) {
                MusicState.RELEASE -> {
                    try {
                        if(mediaPlayer == null) {
                            mediaPlayer = MediaPlayer.create(this@SongDetailActivity, R.raw.sample).apply {
                                setOnCompletionListener {
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    job?.cancel()
                                    currentPlayTime = 0
                                    tvSongTimeStart.text = getFormattedTime(currentPlayTime.toInt())
                                    musicState = MusicState.RELEASE
                                    seekbarDetailMusic.progress = 0
                                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                                }
                            }
                        }
                        mediaPlayer?.start()
                        job = lifecycleScope.launch {
                            while (isActive) {
                                currentPlayTime += 100L
                                tvSongTimeStart.text = getFormattedTime(currentPlayTime.toInt())
                                seekbarDetailMusic.progress = mediaPlayer?.currentPosition ?: 0
                                delay(100L)
                            }
                        }
                        musicState = MusicState.PLAYING
                        ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_pause))
                    } catch (e: IOException) {
                        Toast.makeText(this@SongDetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                MusicState.PLAYING -> {
                    mediaPlayer?.pause()
                    job?.cancel()
                    musicState = MusicState.PAUSE
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_play_filled))
                }
                MusicState.PAUSE -> {
                    mediaPlayer?.start()
                    job = lifecycleScope.launch {
                        while (isActive) {
                            currentPlayTime += 100L
                            tvSongTimeStart.text = getFormattedTime(currentPlayTime.toInt())
                            seekbarDetailMusic.progress = mediaPlayer?.currentPosition ?: 0
                            delay(100L)
                        }
                    }
                    musicState = MusicState.PLAYING
                    ivMusicPlay.setImageDrawable(ContextCompat.getDrawable(this@SongDetailActivity, R.drawable.ic_pause))
                }
            }
        }
        ivMusicPlayPrevious.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
        ivMusicPlayNext.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.prepare()
            mediaPlayer?.start()
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

    private fun getFormattedTime(milliseconds: Int): String {
        val totalSecond = milliseconds/1000
        val minutes = totalSecond/60
        val seconds = totalSecond%60
        return String.format("%02d:%02d", minutes, seconds)
    }
}