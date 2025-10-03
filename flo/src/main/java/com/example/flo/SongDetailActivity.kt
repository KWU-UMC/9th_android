package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.databinding.ActivitySongDetailBinding

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding
    private var isShuffleOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraPadding = resources.getDimensionPixelSize(R.dimen.activity_default_padding)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + extraPadding, systemBars.top + extraPadding, systemBars.right + extraPadding, systemBars.bottom + extraPadding)
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
        ivMusicShuffle.setOnClickListener {
            if(isShuffleOn) {
                isShuffleOn = false
                ivMusicShuffle.setColorFilter(ContextCompat.getColor(this@SongDetailActivity, R.color.ic_state_inactivate))
                Toast.makeText(this@SongDetailActivity, "순차적으로 재생됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                isShuffleOn = true
                ivMusicShuffle.setColorFilter(ContextCompat.getColor(this@SongDetailActivity, R.color.ic_state_activate))
                Toast.makeText(this@SongDetailActivity, "무작위로 재생됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}