package com.example.flo.mission.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flo.mission.data.local.room.SongConverter
import com.example.flo.mission.data.local.room.dao.AlbumDao
import com.example.flo.mission.data.local.room.dao.SongDao
import com.example.flo.mission.data.local.room.entity.AlbumEntity
import com.example.flo.mission.data.local.room.entity.SongEntity

@Database(entities = [SongEntity::class, AlbumEntity::class], version = 2)
@TypeConverters(SongConverter::class)
abstract class MusicDatabase: RoomDatabase() {

    abstract val songDao: SongDao
    abstract val albumDao: AlbumDao

    companion object {
        @Volatile
        private var INSTANCE: MusicDatabase? = null
        fun getInstance(context: Context): MusicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = MusicDatabase::class.java,
                    name = DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }

        private const val DATABASE_NAME = "database_music"
    }
}