package com.example.flo.mission.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String = "", // PK
    val password: String = ""
)
