@file:Suppress("unused", "unused")

package com.dicoding.githser.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githser.data.response.GithubFollowListResponseItem
import com.dicoding.githser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("unused")
class FollowListViewModel : ViewModel() {
    private val _listFollow = MutableLiveData<List<GithubFollowListResponseItem>>()
    val listFollow: LiveData<List<GithubFollowListResponseItem>> = _listFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowListViewModel"
    }


    fun getFollower(username: String) {
        _isLoading.value = true
        val listFollower = ApiConfig.getApiService().getFollowers(username)
        listFollower.enqueue(object : Callback<List<GithubFollowListResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubFollowListResponseItem>>,
                response: Response<List<GithubFollowListResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { followers ->
                        if (followers.isEmpty()) {
                            _listFollow.value = emptyList()
                        } else {
                            _listFollow.value = followers
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubFollowListResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val listFollower = ApiConfig.getApiService().getFollowing(username)
        listFollower.enqueue(object : Callback<List<GithubFollowListResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubFollowListResponseItem>>,
                response: Response<List<GithubFollowListResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { followers ->
                        if (followers.isEmpty()) {
                            _listFollow.value = emptyList()
                        } else {
                            _listFollow.value = followers
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubFollowListResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}