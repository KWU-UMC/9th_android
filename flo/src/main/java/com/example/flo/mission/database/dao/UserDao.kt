package com.example.flo.mission.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.flo.mission.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)
}