package com.dicoding.githser.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githser.data.database.Favorite


class FavoriteDiffCallback(
    private val oldFavoriteList: List<Favorite>,
    private val newFavoritList: List<Favorite>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoritList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoritList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoritList[newItemPosition]

        return oldFavorite.username == newFavorite.username && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }

}