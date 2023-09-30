package com.bangkit.submissionfundamentalsatu.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase: RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao


    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null
        fun getInstance(
            context: Context
        ): FavoriteUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java, "FavoriteUser.db"
                ).build()
            }
    }
}