package com.bangkit.submissionfundamentalsatu.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity


@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(userEntity: UserEntity)

    @Query("DELETE FROM favorite_user WHERE username = :username")
    suspend fun deleteFavoriteUser(username: String)

    @Query("SELECT EXISTS (SELECT * FROM favorite_user WHERE username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>
}