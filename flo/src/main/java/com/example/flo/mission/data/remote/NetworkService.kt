package com.example.flo.mission.data.remote

import com.example.flo.mission.data.remote.dto.AuthResponse
import com.example.flo.mission.data.remote.dto.LoginData
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.data.remote.dto.UpdateMemberRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface NetworkService {
    @POST("/signup")
    suspend fun signup(
        @Body req: SignUpRequest
    ): Response<AuthResponse<MemberIdResponse>>

    @POST("/login")
    suspend fun login(
        @Body req: LoginRequest
    ): Response<AuthResponse<LoginData>>

    @PATCH("/users")
    suspend fun changeInfo(
        @Header("Authorization") token: String,
        @Body req: UpdateMemberRequest
    ): Response<AuthResponse<MemberIdResponse>>
}