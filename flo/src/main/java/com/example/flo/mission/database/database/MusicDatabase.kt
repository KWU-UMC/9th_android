package com.example.flo.mission.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flo.mission.database.SongConverter
import com.example.flo.mission.database.dao.AlbumDao
import com.example.flo.mission.database.dao.SongDao
import com.example.flo.mission.database.entity.AlbumEntity
import com.example.flo.mission.database.entity.SongEntity

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