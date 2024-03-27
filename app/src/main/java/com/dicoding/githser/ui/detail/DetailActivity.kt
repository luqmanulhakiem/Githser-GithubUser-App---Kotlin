@file:Suppress("SpellCheckingInspection")

package com.dicoding.githser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githser.R
import com.dicoding.githser.data.database.Favorite
import com.dicoding.githser.data.database.FavoriteDao
import com.dicoding.githser.data.database.FavoriteRoomDatabase
import com.dicoding.githser.data.helper.ViewModelFactory
import com.dicoding.githser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding
    private var favorite: Favorite? = null

    private var isFavorite = false

    private lateinit var favoriteAddViewModel: FavoriteAddViewModel
    private lateinit var db: FavoriteRoomDatabase
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var userNameuser: String
    private lateinit var userAvatarUrl: String


    companion object {
        const val KEY_USERNAME = "key_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FavoriteRoomDatabase.getDatabase(applicationContext)
        favoriteDao = db.favoriteDao()

        val detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val userName = intent.getStringExtra(KEY_USERNAME)
        val username: TextView = findViewById(R.id.tiUsername)
        val name: TextView = findViewById(R.id.tiName)

        userName?.let {
            detailViewModel.getDetailUser(it)
        }
        val tabs: TabLayout = findViewById(R.id.tabs)

        detailViewModel.detailUser.observe(this) { user ->
            userNameuser = user.login
            userAvatarUrl = user.avatarUrl
            username.text = user.login
            name.text = user.name
            Glide.with(this).load("${user.avatarUrl}}").into(binding.tiAvatar)
            tabs.getTabAt(0)?.text = resources.getString(TAB_TITLES[0], user.followers)
            tabs.getTabAt(1)?.text = resources.getString(TAB_TITLES[1], user.following)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val favoriteCheck = obtainViewModel(this@DetailActivity)
        favoriteCheck.getUserFavorite(userName.toString()).observe(this) { favUser ->
            if (favUser != null) {
                isFavorite = true
                binding.fabFav.setImageResource(R.drawable.ic_favorite_selected)
            } else {
                isFavorite = false
                binding.fabFav.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userName.toString())
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        favoriteAddViewModel = obtainViewModel(this@DetailActivity)

        binding.fabFav.setOnClickListener(this)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteAddViewModel::class.java]
    }


    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        val userName = userNameuser
        val userAvatar = userAvatarUrl
        favorite = Favorite(userName, userAvatar)
        val tag = "DetailActivity"
        when {
            userName.isEmpty() -> {
                Log.d(tag, "Username Kosong")
            }

            userAvatar.isEmpty() -> {
                Log.d(tag, "Avatar Kosong")
            }

            else -> {
                if (isFavorite) {
                    favoriteAddViewModel.delete(favorite as Favorite)
                    Toast.makeText(applicationContext, "Dihapus dari favorite", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    favoriteAddViewModel.insert(favorite as Favorite)
                    Toast.makeText(
                        applicationContext,
                        "Ditambahkan ke favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}