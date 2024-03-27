package com.dicoding.githser.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githser.R
import com.dicoding.githser.data.helper.ViewModelSettingFactory
import com.dicoding.githser.data.response.ItemsItem
import com.dicoding.githser.databinding.ActivityMainBinding
import com.dicoding.githser.ui.detail.DetailActivity
import com.dicoding.githser.ui.favorites.FavoritesActivity
import com.dicoding.githser.ui.settings.SettingActivity
import com.dicoding.githser.ui.settings.SettingPreferences
import com.dicoding.githser.ui.settings.SettingViewModel
import com.dicoding.githser.ui.settings.dataStore

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelSettingFactory(pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                binding.menuBar.menu.findItem(R.id.menu_fav).icon?.setTint(Color.WHITE)
                binding.menuBar.menu.findItem(R.id.menu_setting).icon?.setTint(Color.WHITE)
            } else {
                binding.menuBar.menu.findItem(R.id.menu_fav).icon?.setTint(Color.BLACK)
                binding.menuBar.menu.findItem(R.id.menu_setting).icon?.setTint(Color.BLACK)
            }
        }

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { listUser ->
            setListUserData(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.menuBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_fav -> {
                    val moveToFavorites = Intent(this@MainActivity, FavoritesActivity::class.java)
                    startActivity(moveToFavorites)
                    true
                }

                R.id.menu_setting -> {
                    val moveToSetting = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(moveToSetting)
                    true
                }

                else -> false
            }
        }

        /**
         * Search
         * */
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                mainViewModel.findUser(searchView.text.toString())
                false
            }
        }
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setListUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(this)
        adapter.submitList(listUser)
        binding.rvList.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val intentToDetail = Intent(this, DetailActivity::class.java)
        val user = (binding.rvList.adapter as UserAdapter).currentList[position]
        intentToDetail.putExtra(DetailActivity.KEY_USERNAME, user.login)
        startActivity(intentToDetail)
    }
}