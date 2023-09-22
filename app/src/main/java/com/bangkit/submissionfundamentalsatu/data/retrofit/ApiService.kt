package com.bangkit.submissionfundamentalsatu.data.retrofit

import com.bangkit.submissionfundamentalsatu.data.response.DetailUserResponse
import com.bangkit.submissionfundamentalsatu.data.response.GithubResponse
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}