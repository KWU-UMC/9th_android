package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.LoginResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.domain.model.SignupStatus
import com.example.flo.mission.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<SignupStatus>()
    val signupResult: LiveData<SignupStatus> = _signupResult

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            val request = SignUpRequest(name = name, email = email, password = password)
            val result = repository.remoteSignUp(req = request)
            result.onSuccess { data ->
                // data 에 memberId 들어 있는데 이걸로 뭐할지..?
                val result = repository.localInsertUser(email = email, password = password)
                result.onSuccess {
                    _signupResult.postValue(SignupStatus.Success)
                }.onFailure { error ->
                    _signupResult.postValue(SignupStatus.LocalError(error))
                }
            }.onFailure { error ->
                // 서버 등록에 실패한 경우
                _signupResult.postValue(SignupStatus.RemoteError(error))
            }
        }
    }

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(email = email, password = password)
            val result = repository.remoteLogin(req = request)
            result.onSuccess { data ->
                repository.localSaveUserId(memberId = data.memberId)
                _loginResult.postValue(result)
            }.onFailure { error ->
                // 서버 로그인 실패
                _loginResult.postValue(Result.failure(error))
            }
        }
    }

    private val _testResult = MutableLiveData<Result<String>>()
    val testResult: LiveData<Result<String>> = _testResult

    fun test(accessToken: String) {
        viewModelScope.launch {
            val result = repository.testAccessToken(accessToken = accessToken)
            _testResult.postValue(result)
        }
    }


}