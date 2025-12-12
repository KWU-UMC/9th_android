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
import com.example.flo.mission.data.remote.NetworkClient
import com.example.flo.mission.domain.model.SignupStatus
import com.example.flo.mission.domain.repository.AuthRepository
import com.example.flo.mission.presentation.AuthViewModel
import com.example.flo.mission.presentation.AuthViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(repository = AuthRepository(networkService = NetworkClient.networkService))
    }

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
            val email = etSignUpEmail.text.toString().trim()
            val password = etSignUpPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.signup(name = name, email = email, password = password)
        }
    }


    private fun initObservers() = with(binding) {
        authViewModel.signupResult.observe(this@SignUpActivity) { status ->
            when(status) {
                is SignupStatus.Success -> {
                    Toast.makeText(
                        this@SignUpActivity,
                        "회원가입이 완료되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is SignupStatus.RemoteError -> {
                    Toast.makeText(this@SignUpActivity, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                is SignupStatus.LocalError -> {
                    Toast.makeText(this@SignUpActivity, "서버에는 저장 됐지만 내부 저장소 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}