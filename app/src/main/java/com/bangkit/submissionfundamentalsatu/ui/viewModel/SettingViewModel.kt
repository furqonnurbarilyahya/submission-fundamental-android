package com.bangkit.submissionfundamentalsatu.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionfundamentalsatu.data.source.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preferencesData: SettingPreferences) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return preferencesData.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferencesData.saveThemeSetting(isDarkMode)
        }
    }

}