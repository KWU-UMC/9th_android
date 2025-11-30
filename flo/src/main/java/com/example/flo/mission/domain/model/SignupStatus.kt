package com.example.flo.mission.domain.model

sealed class SignupStatus {
    data object Success: SignupStatus()
    data class RemoteError(val error: Throwable): SignupStatus()
    data class LocalError(val error: Throwable): SignupStatus()
}