@file:Suppress("unused", "unused")

package com.dicoding.githser.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githser.data.database.Favorite
import com.dicoding.githser.data.helper.FavoriteDiffCallback
import com.dicoding.githser.databinding.ItemFavoriteBinding

@Suppress("unused")
class FavoriteAdapter(
    private val onCardClick: (Favorite) -> Unit,
    private val onIconClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<Favorite>()

    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    inner class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: Favorite) {
            with(binding) {
                tvItemUsername.text = favorite.username
                Glide.with(context).load("${favorite.avatarUrl}}").into(binding.ivAvatar)
                icFav.setOnClickListener {
                    onIconClick(favorite)
                }
                cvItemFavorite.setOnClickListener {
                    onCardClick(favorite)
                }
            }
        }

    }

}