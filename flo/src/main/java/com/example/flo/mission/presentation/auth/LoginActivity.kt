package com.example.flo.mission.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.flo.R
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.mission.data.remote.NetworkClient
import com.example.flo.mission.domain.repository.AuthRepository
import com.example.flo.mission.presentation.AuthViewModel
import com.example.flo.mission.presentation.AuthViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(repository = AuthRepository(networkService = NetworkClient.networkService))
    }

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
        btnSocialLogin.setOnClickListener {
            // 카카오톡 로그인을 할 수 없어 카카오게정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if(error != null) {
                    Log.e(KAKAO, "카카오 계정으로 로그인 실패", error)
                } else if(token != null) {
                    Log.e(KAKAO, "카카오 계정으로 로그인 성공 ${token.accessToken}")
                    fetchUserKakaoAccount()
                }
            }

            if(UserApiClient.instance.isKakaoTalkLoginAvailable(context = this@LoginActivity)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(context = this@LoginActivity) { token, error ->
                    if(error != null) {
                        Log.e(KAKAO, "카카오톡으로 로그인 실패", error)
                        // 사용자의 의도적인 로그인 취소 → 카카오 계정 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if(error is ClientError && error.reason == ClientErrorCause.Cancelled) return@loginWithKakaoTalk
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context = this@LoginActivity, callback = callback)
                    } else if(token != null) {
                        Log.e(KAKAO, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        fetchUserKakaoAccount()
                    }
                }
            } else {
                // 카카오 계정 로그인
                UserApiClient.instance.loginWithKakaoAccount(context = this@LoginActivity, callback = callback)
            }
        }
        btnSocialLogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if(error != null) {
                    Toast.makeText(this@LoginActivity, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "로그아웃에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    llSocialLoginInfo.isVisible = false
                    btnSocialLogin.isEnabled = true
                    btnSocialLogin.alpha = 1f
                }
            }
        }
        tvGoToSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initObservers() = with(binding) {
        authViewModel.loginResult.observe(this@LoginActivity) { result ->
            result.onSuccess { response ->
                Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure { error ->
                Toast.makeText(this@LoginActivity, "로그인 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUserKakaoAccount() = with(binding) {
        UserApiClient.instance.me { user, error ->
            if(error!= null) {
                Log.e(KAKAO, "사용자 정보 요청 실패", error)
            } else if(user != null) {
                val id = user.id
                val profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                val nickname = user.kakaoAccount?.profile?.nickname
                val email = user.kakaoAccount?.email
                Glide.with(ivSocialProfile).load(profile).into(ivSocialProfile)
                tvSocialId.text = "아이디: $id"
                tvSocialNickname.text = "닉네임: $nickname"
                tvSocialEmail.text = "이메일: $email"
                llSocialLoginInfo.isVisible = true
                btnSocialLogin.isEnabled = false
                btnSocialLogin.alpha = 0.3f
            }
        }
    }

    companion object {
        private const val KAKAO = "kakao"
    }
}