package com.example.flo.mission.data.remote

import com.example.flo.mission.data.remote.dto.AuthResponse
import com.example.flo.mission.data.remote.dto.ChangeData
import com.example.flo.mission.data.remote.dto.ChangeRequest
import com.example.flo.mission.data.remote.dto.LoginData
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.SignUpData
import com.example.flo.mission.data.remote.dto.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface NetworkService {
    @POST
    suspend fun signup(
        @Body req: SignUpRequest
    ): Response<AuthResponse<SignUpData>>

    @POST
    suspend fun login(
        @Body req: LoginRequest
    ): Response<AuthResponse<LoginData>>

    @PATCH
    suspend fun changeInfo(
        @Header("Authorization") token: String,
        @Body req: ChangeRequest
    ): Response<AuthResponse<ChangeData>>
}