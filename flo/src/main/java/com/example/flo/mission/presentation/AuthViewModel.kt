package com.example.flo.mission.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    // 정보
    var memberId: Int? = null

    private val _signupResult = MutableLiveData<Result<MemberIdResponse>>()
    val signupResult: LiveData<Result<MemberIdResponse>> = _signupResult

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            val request = SignUpRequest(name = name, email = email, password = password)
            val result = repository.signup(req = request)
            _signupResult.postValue(result)
        }
    }
}