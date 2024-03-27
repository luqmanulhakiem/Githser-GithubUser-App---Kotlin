package com.dicoding.githser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githser.data.database.Favorite
import com.dicoding.githser.data.database.FavoriteDao
import com.dicoding.githser.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()
    fun getUserFavorite(username: String): LiveData<Favorite> =
        mFavoriteDao.getFavoriteUserByUsername(username)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}