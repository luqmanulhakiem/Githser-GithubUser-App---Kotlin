package com.dicoding.githser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githser.data.database.Favorite
import com.dicoding.githser.data.repository.FavoriteRepository

class FavoriteAddViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun getUserFavorite(username: String): LiveData<Favorite> =
        mFavoriteRepository.getUserFavorite(username)
}