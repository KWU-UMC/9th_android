package com.example.flo.mission.presentation.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.R
import com.example.flo.databinding.ActivitySignUpBinding
import com.example.flo.mission.data.local.room.RoomDatabaseModule.userDao
import com.example.flo.mission.data.local.room.database.UserDatabase
import com.example.flo.mission.data.local.room.entity.UserEntity
import com.example.flo.mission.data.remote.NetworkClient
import com.example.flo.mission.domain.repository.AuthRepository
import com.example.flo.mission.presentation.AuthViewModel
import com.example.flo.mission.presentation.AuthViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(repository = AuthRepository(networkService = NetworkClient.networkService))
    }
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extraPadding = resources.getDimensionPixelSize(R.dimen.activity_default_padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + extraPadding,
                systemBars.top + extraPadding,
                systemBars.right + extraPadding,
                systemBars.bottom + extraPadding
            )
            insets
        }
        initListeners()
        initObservers()
    }

    private fun initListeners() = with(binding) {
        btnSignUp.setOnClickListener {
            val name = etSignUpName.text.toString().trim()
            email = etSignUpEmail.text.toString().trim()
            password = etSignUpPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // remote (api)
            authViewModel.signup(name = name, email = email, password = password)
        }
    }


    private fun initObservers() = with(binding) {
        authViewModel.signupResult.observe(this@SignUpActivity) { result ->
            result.onSuccess { data ->
                // data가 가지고 있는 정보 → memberId: Int
                authViewModel.insertUser(email = email, password = password)
            }.onFailure { error ->
                // 네트워크 불안정, 중복 회원가입
                val message = error.message ?: "알 수 없는 오류가 발생했습니다."
                Toast.makeText(this@SignUpActivity, "회원가입에 실패: $message", Toast.LENGTH_SHORT).show()
            }
        }
        authViewModel.insertUserResult.observe(this@SignUpActivity) { result ->
            result.onSuccess {
                Toast.makeText(
                    this@SignUpActivity,
                    "회원가입이 완료되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                finish() // 로그인 화면으로 돌아가기
            }
        }
    }
}