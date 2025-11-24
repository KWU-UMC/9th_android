package com.example.flo.mission.presentation.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.R
import com.example.flo.databinding.ActivitySignUpBinding
import com.example.flo.mission.data.local.room.database.UserDatabase
import com.example.flo.mission.data.local.room.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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
        btnSignUp.setOnClickListener {
            if(etSignUpEmail.text.isBlank() || etSignUpPassword.text.isBlank()) {
                Toast.makeText(this@SignUpActivity, "정보를 다 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val database = UserDatabase.getInstance(context = this@SignUpActivity)
                val userDao = database.userDao

                // 메인 스레드가 아닌 UI 스레드에서 DB 작업 실행
                CoroutineScope(Dispatchers.IO).launch {
                    val email = etSignUpEmail.text.trim().toString()
                    val password = etSignUpPassword.text.trim().toString()
                    val user: UserEntity? = userDao.getUserByEmail(email = email)
                    if(user == null) {
                        userDao.insertUser(user = UserEntity(email = email, password = password))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "중복된 아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}