package com.dicoding.githser.ui.follow

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githser.data.response.GithubFollowListResponseItem
import com.dicoding.githser.databinding.ItemReviewBinding


class FollowAdapter :
    ListAdapter<GithubFollowListResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemReviewBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubFollowListResponseItem) {
            binding.tvItem.text = user.login
            Glide.with(context).load("${user.avatarUrl}}").into(binding.tvPicture)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubFollowListResponseItem>() {
            override fun areItemsTheSame(
                oldItem: GithubFollowListResponseItem, newItem: GithubFollowListResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubFollowListResponseItem, newItem: GithubFollowListResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}