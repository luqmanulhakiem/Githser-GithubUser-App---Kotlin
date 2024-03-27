package com.dicoding.githser.data.retrofit

import com.dicoding.githser.data.response.GithubDetailUserResponse
import com.dicoding.githser.data.response.GithubFollowListResponseItem
import com.dicoding.githser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUserByQuery(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<GithubDetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubFollowListResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubFollowListResponseItem>>
}