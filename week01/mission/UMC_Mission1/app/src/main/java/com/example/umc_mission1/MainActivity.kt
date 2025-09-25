package com.example.umc_mission1

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun addChangeColorEvent(textViewId: Int, imageViewId: Int, color: String) {
            val imageView = findViewById<ImageView>(imageViewId)
            val textView = findViewById<TextView>(textViewId)

            imageView.setOnClickListener {
                val myToast = Toast.makeText(this.getApplicationContext(), "색상을 변경했습니다.", Toast.LENGTH_SHORT)
                myToast.show()
                textView.setTextColor(Color.parseColor(color))
            }
        }

        addChangeColorEvent(R.id.angry_tv, R.id.angry_iv, "#EB8B8B")
        addChangeColorEvent(R.id.happy_tv, R.id.happy_iv, "#FFEFB6")
        addChangeColorEvent(R.id.excited_tv, R.id.excited_iv, "#CEE7F5")
        addChangeColorEvent(R.id.normal_tv, R.id.normal_iv, "#BEC3ED")
        addChangeColorEvent(R.id.anxiety_tv, R.id.anxiety_iv, "#B1D3B9")
    }
}