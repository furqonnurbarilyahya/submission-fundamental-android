package com.bangkit.submissionfundamentalsatu.ui.viewModel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.submissionfundamentalsatu.data.source.SettingPreferences
import com.bangkit.submissionfundamentalsatu.data.source.UserRepository
import com.bangkit.submissionfundamentalsatu.di.Injection

val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "settings")
class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val preferences: SettingPreferences
): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(
            context: Context
        ): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context), SettingPreferences.getInstance(context.dataStore))
            }.also { INSTANCE = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}