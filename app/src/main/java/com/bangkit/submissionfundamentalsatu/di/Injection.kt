package com.bangkit.submissionfundamentalsatu.di

import android.content.Context
import com.bangkit.submissionfundamentalsatu.data.source.UserRepository
import com.bangkit.submissionfundamentalsatu.database.room.FavoriteUserDatabase
import com.bangkit.submissionfundamentalsatu.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        return UserRepository.getInstance(dao)
    }
}