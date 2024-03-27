package com.dicoding.githser.ui.favorites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githser.data.helper.ViewModelFactory
import com.dicoding.githser.databinding.ActivityFavoritesBinding
import com.dicoding.githser.ui.detail.DetailActivity
import com.dicoding.githser.ui.detail.FavoriteAddViewModel

class FavoritesActivity : AppCompatActivity() {
    private var _activityFavoritesBinding: ActivityFavoritesBinding? = null
    private val binding get() = _activityFavoritesBinding
    private lateinit var favoriteAddViewModel: FavoriteAddViewModel

    private lateinit var adapter: FavoriteAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoritesBinding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val favoritesViewModel = obtainViewModel(this@FavoritesActivity)
        favoritesViewModel.getAllFavorites().observe(this) { favoritesList ->
            if (favoritesList.isNotEmpty()) {
                adapter.setListFavorites(favoritesList)
                binding?.rvFavorites?.visibility = View.VISIBLE
                binding?.lvEmpty?.visibility = View.GONE
            } else {
                binding?.rvFavorites?.visibility = View.GONE
                binding?.lvEmpty?.visibility = View.VISIBLE
            }
        }

        favoriteAddViewModel = obtainViewModel2(this@FavoritesActivity)
        adapter = FavoriteAdapter(
            onCardClick = { favorite ->
                val moveToDetail = Intent(this@FavoritesActivity, DetailActivity::class.java)
                moveToDetail.putExtra(DetailActivity.KEY_USERNAME, favorite.username)
                startActivity(moveToDetail)
            },
            onIconClick = { favorite ->
                favoriteAddViewModel.delete(favorite)
            },
        )

        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(true)
        binding?.rvFavorites?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoritesBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }

    private fun obtainViewModel2(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteAddViewModel::class.java]
    }
}