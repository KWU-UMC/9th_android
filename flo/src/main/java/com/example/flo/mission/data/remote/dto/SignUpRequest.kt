package com.example.flo.mission.data.remote.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

data class SignUpData(
    val memberId: Int
)

////////////////////////////////////////////////////////////////

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse<T>(
    val status: Boolean,
    val code: String,
    val message: String,
    val data: T? = null
)

data class LoginData(
    val name: String,
    val memberId: Int,
    val accessToken: String
)

////////////////////////////////////////////////////////////////

data class ChangeRequest(
    val memberId: Int,
    val newName: String,
    val newPassword: String
)

data class ChangeData(
    val memberId: Int
)