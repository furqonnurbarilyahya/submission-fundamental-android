package com.bangkit.submissionfundamentalsatu.data.source

import androidx.lifecycle.LiveData
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity
import com.bangkit.submissionfundamentalsatu.database.room.FavoriteUserDao

class UserRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao
){

    companion object {
        private var INSTANCE: UserRepository? = null
        fun getInstance (
            userDao: FavoriteUserDao
        ): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(userDao)
            }.also { INSTANCE = it }
    }
    suspend fun insertFavoriteUser (userEntity: UserEntity) {
        favoriteUserDao.insertFavoriteUser(userEntity)
    }

    suspend fun deleteFavoriteUser(username: String) {
        favoriteUserDao.deleteFavoriteUser(username)
    }

    fun getAllFavoriteUser(): LiveData<List<UserEntity>> = favoriteUserDao.getAllFavoriteUser()

    fun isFavoriteUser(username: String): LiveData<Boolean> = favoriteUserDao.isFavorite(username)

}