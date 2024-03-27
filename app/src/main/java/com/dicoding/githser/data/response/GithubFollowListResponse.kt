package com.dicoding.githser.data.response

import com.google.gson.annotations.SerializedName

data class GithubFollowListResponseItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)
