package com.bangkit.submissionfundamentalsatu.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submissionfundamentalsatu.data.response.GithubResponse
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user : LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUser()
    }

    fun searchUser (query: String = "furqon") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}