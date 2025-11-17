package com.example.flo.mission.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.R
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.mission.database.database.UserDatabase
import com.example.flo.mission.database.entity.UserEntity
import com.example.flo.mission.database.pref.UserPreference
import com.example.flo.mission.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extraPadding = resources.getDimensionPixelSize(R.dimen.activity_default_padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + extraPadding, systemBars.top + extraPadding, systemBars.right + extraPadding, systemBars.bottom + extraPadding)
            insets
        }
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnLogin.setOnClickListener {
            if(etLoginEmail.text.isBlank() || etLoginPassword.text.isBlank()) {
                Toast.makeText(this@LoginActivity, "로그인 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val database = UserDatabase.getInstance(context = this@LoginActivity)
                val userDao = database.userDao
                CoroutineScope(Dispatchers.IO).launch {
                    val user: UserEntity? = userDao.getUserByEmail(email = etLoginEmail.text.toString())
                    if(user == null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else if(user.password != etLoginPassword.text.toString()) {
                        // 유저 정보가 있지만 패스워드가 틀린 경우
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "패스워드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 유저 정보도 있고 패스워드도 맞는 경우
                        val userPref = UserPreference(context = this@LoginActivity)
                        userPref.setUserId(id = user.id)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        tvGoToSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}