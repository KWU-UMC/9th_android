package com.example.flo.mission.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flo.mission.data.local.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM usertable WHERE email = :email")
    fun getUserByEmail(email: String): UserEntity?
}