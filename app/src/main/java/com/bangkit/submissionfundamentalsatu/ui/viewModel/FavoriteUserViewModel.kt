package com.bangkit.submissionfundamentalsatu.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submissionfundamentalsatu.data.source.UserRepository
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity

class FavoriteUserViewModel (private val userRepository: UserRepository) : ViewModel() {
    companion object {
        const val TAG = "FavoriteUserViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoriteuser(): LiveData<List<UserEntity>>{
        _isLoading.value = false
        return userRepository.getAllFavoriteUser()
    }
}