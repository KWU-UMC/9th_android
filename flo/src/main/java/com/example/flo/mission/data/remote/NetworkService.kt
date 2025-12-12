package com.example.flo.mission.data.remote

import com.example.flo.mission.data.remote.dto.AuthResponse
import com.example.flo.mission.data.remote.dto.LoginResponse
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.data.remote.dto.TestResponse
import com.example.flo.mission.data.remote.dto.UpdateMemberRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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
    ): Response<AuthResponse<LoginResponse>>

    @GET("/test")
    suspend fun test(
        @Header("Authorization") token: String
    ): Response<AuthResponse<TestResponse>>

    @PATCH("/users")
    suspend fun changeInfo(
        @Header("Authorization") token: String,
        @Body req: UpdateMemberRequest
    ): Response<AuthResponse<MemberIdResponse>>
}