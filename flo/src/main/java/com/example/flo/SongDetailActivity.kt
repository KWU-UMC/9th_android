package com.example.flo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.databinding.ActivitySongDetailBinding

class SongDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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
    }
}