package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flo.mission.data.remote.dto.LoginData
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    private val _signupResult = MutableLiveData<Result<MemberIdResponse>>()
    val signupResult: LiveData<Result<MemberIdResponse>> = _signupResult

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            val request = SignUpRequest(name = name, email = email, password = password)
            val result = repository.remoteSignUp(req = request)
            _signupResult.postValue(result)
        }
    }

    private val _insertUserResult = MutableLiveData<Result<Unit>>()
    val insertUserResult: LiveData<Result<Unit>> = _insertUserResult

    fun insertUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.localInsertUser(email = email, password = password)
            _insertUserResult.postValue(result)
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