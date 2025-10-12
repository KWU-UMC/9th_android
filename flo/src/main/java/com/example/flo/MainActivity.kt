package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var getResultFromSongActivity: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }
    
    companion object {
        const val INTENT_KEY = "key"
        const val TITLE = "title"
        const val SINGER = "singer"
    }
}