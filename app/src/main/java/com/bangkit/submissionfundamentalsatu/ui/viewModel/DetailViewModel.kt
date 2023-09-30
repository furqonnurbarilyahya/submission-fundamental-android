package com.bangkit.submissionfundamentalsatu.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.submissionfundamentalsatu.data.response.DetailUserResponse
import com.bangkit.submissionfundamentalsatu.data.retrofit.ApiConfig
import com.bangkit.submissionfundamentalsatu.data.source.UserRepository
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val mUserRepository: UserRepository) : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse> ()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    init {
        detailUser()
    }

    fun detailUser (username: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "On failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFalure: ${t.message}")
            }

        })
    }

    fun insertFavoriteUser(userEntity: UserEntity) {
        viewModelScope.launch {
            mUserRepository.insertFavoriteUser(userEntity)
        }
    }

    fun deleteFavoriteUser(username: String) {
        viewModelScope.launch {
            mUserRepository.deleteFavoriteUser(username)
        }
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> {
        return mUserRepository.isFavoriteUser(username)
    }
}