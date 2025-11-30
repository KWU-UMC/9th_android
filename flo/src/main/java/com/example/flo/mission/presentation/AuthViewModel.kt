package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flo.mission.data.remote.dto.LoginData
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.domain.model.SignupStatus
import com.example.flo.mission.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
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

    private val _loginResult = MutableLiveData<Result<LoginData>>()
    val loginResult: LiveData<Result<LoginData>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(email = email, password = password)
            val result = repository.login(req = request)
            _loginResult.postValue(result)
        }
    }


}