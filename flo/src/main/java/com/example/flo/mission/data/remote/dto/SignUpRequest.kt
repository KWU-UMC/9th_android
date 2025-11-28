package com.example.flo.mission.data.remote.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
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

data class UpdateMemberRequest(
    val memberId: Int,
    val newName: String,
    val newPassword: String
)

data class MemberIdResponse(
    val memberId: Int
)