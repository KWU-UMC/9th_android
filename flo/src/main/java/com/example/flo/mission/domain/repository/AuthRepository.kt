package com.example.flo.mission.domain.repository

import com.example.flo.mission.data.remote.NetworkService
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.data.remote.dto.UpdateMemberRequest

class AuthRepository(private val networkService: NetworkService) {

    suspend fun signup(req: SignUpRequest): Result<MemberIdResponse> = try {
        val response = networkService.signup(req = req)
        if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(RuntimeException("response body is null"))
            } else if (body.data == null) {
                Result.failure(RuntimeException("response is ok but data is null"))
            } else {
                Result.success(body.data)
            }
        } else {
            val errMsg = response.errorBody()?.string() ?: response.message()
            Result.failure(RuntimeException("HTTP ${response.code()}: $errMsg"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun changeInfo(accessToken: String, req: UpdateMemberRequest): Result<MemberIdResponse> = try {
        val token = if(accessToken.startsWith("Bearer ")) accessToken else "Bearer $accessToken"
        val response = networkService.changeInfo(token = token, req = req)
        if(response.isSuccessful) {
            val body = response.body()
            if(body == null) {
                Result.failure(RuntimeException("Response body is null"))
            } else if(body.data == null) {
                Result.failure(RuntimeException("Response is ok but data is null"))
            } else {
                Result.success(body.data)
            }
        } else {
            val errMsg = response.errorBody()?.string() ?: response.message()
            Result.failure(RuntimeException("HTTP ${response.code()}: $errMsg"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}