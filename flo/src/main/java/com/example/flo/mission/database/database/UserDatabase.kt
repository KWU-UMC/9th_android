package com.example.flo.mission.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo.mission.database.dao.UserDao
import com.example.flo.mission.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = UserDatabase::class.java,
                    name = DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
        private const val DATABASE_NAME = "database_user"
    }
}