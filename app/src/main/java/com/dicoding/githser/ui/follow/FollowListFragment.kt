package com.dicoding.githser.ui.follow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githser.data.response.GithubFollowListResponseItem
import com.dicoding.githser.databinding.FragmentFollowListBinding

class FollowListFragment : Fragment() {

    private var position: Int = 0
    private var username: String? = null

    private lateinit var binding: FragmentFollowListBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowListBinding.inflate(inflater, container, false)

        val followListViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowListViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1) {
            followListViewModel.getFollower(username.toString())
        } else {
            followListViewModel.getFollowing(username.toString())
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        followListViewModel.listFollow.observe(viewLifecycleOwner) { listFollow ->
            setListFollow(listFollow)
        }

        followListViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return binding.root
    }

    private fun setListFollow(listFollow: List<GithubFollowListResponseItem>?) {
        val adapter = FollowAdapter()
        adapter.submitList(listFollow)
        binding.rvList.adapter = adapter
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}