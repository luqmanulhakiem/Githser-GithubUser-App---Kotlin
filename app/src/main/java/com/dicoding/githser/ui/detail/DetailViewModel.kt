package com.dicoding.githser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githser.data.response.GithubDetailUserResponse
import com.dicoding.githser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<GithubDetailUserResponse>()
    val detailUser: LiveData<GithubDetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewViewModel"
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val user = ApiConfig.getApiService().getDetailUser(username)
        user.enqueue(object : Callback<GithubDetailUserResponse> {
            override fun onResponse(
                call: Call<GithubDetailUserResponse>, response: Response<GithubDetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubDetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}