package com.example.flo.mission.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.R
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.mission.data.remote.NetworkClient
import com.example.flo.mission.domain.repository.AuthRepository
import com.example.flo.mission.presentation.AuthViewModel
import com.example.flo.mission.presentation.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(repository = AuthRepository(networkService = NetworkClient.networkService))
    }
    private var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        btnLogin.setOnClickListener {
            if (etLoginEmail.text.isBlank() || etLoginPassword.text.isBlank()) {
                Toast.makeText(this@LoginActivity, "로그인 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.login(
                email = etLoginEmail.text.toString(),
                password = etLoginPassword.text.toString()
            )
        }
        tvGoToSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnTokenTest.setOnClickListener {
            accessToken?.let {
                authViewModel.test(accessToken = it)
            } ?: Toast.makeText(this@LoginActivity, "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObservers() = with(binding) {
        authViewModel.loginResult.observe(this@LoginActivity) { result ->
            result.onSuccess { response ->
                accessToken = response.accessToken
                Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
            }.onFailure { error ->
                Toast.makeText(this@LoginActivity, "로그인 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
        authViewModel.testResult.observe(this@LoginActivity) { result ->
            result.onSuccess { testResult ->
                tvTestResult.text = testResult
            }.onFailure { error ->
                Toast.makeText(this@LoginActivity, "테스트 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}