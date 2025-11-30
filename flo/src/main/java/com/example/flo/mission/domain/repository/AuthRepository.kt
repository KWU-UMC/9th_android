package com.example.flo.mission.domain.repository

import com.example.flo.mission.data.local.room.DatabaseModule.userDao
import com.example.flo.mission.data.local.room.DatabaseModule.userPreference
import com.example.flo.mission.data.local.room.entity.UserEntity
import com.example.flo.mission.data.remote.NetworkService
import com.example.flo.mission.data.remote.dto.LoginData
import com.example.flo.mission.data.remote.dto.LoginRequest
import com.example.flo.mission.data.remote.dto.MemberIdResponse
import com.example.flo.mission.data.remote.dto.SignUpRequest
import com.example.flo.mission.data.remote.dto.UpdateMemberRequest

class AuthRepository(private val networkService: NetworkService) {

    /**
     * 회원가입 로직
     * 외부 -> remoteSignUp
     * 내부 -> localInsertUser
     */
    suspend fun remoteSignUp(req: SignUpRequest): Result<MemberIdResponse> = try {
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

    suspend fun localInsertUser(email: String, password: String): Result<Unit> = try {
        userDao.insertUser(user = UserEntity(email = email, password = password))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * 로그인 로직
     */
    suspend fun remoteLogin(req: LoginRequest): Result<LoginData> = try {
        val response = networkService.login(req = req)
        if(response.isSuccessful) {
            val body = response.body()
            if(body == null) {
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

    fun localSaveUserId(memberId: Int) {
        userPreference.setUserId(id = memberId)
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