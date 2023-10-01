package com.bangkit.submissionfundamentalsatu.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.databinding.FragmentFollowerBinding
import com.bangkit.submissionfundamentalsatu.ui.viewModel.FollowViewModel
import com.bangkit.submissionfundamentalsatu.adapter.UserAdapter

class FollowerFollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITON = "position"

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = arguments?.getString(ARG_USERNAME)
        var position = 0

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITON)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            username?.let { detailViewModel.getFollowers(it) }
            detailViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setUserData(it)
            }

        } else {
            username?.let { detailViewModel.getFollowing(it) }
            detailViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            detailViewModel.following.observe(viewLifecycleOwner) {
                setUserData(it)
            }

        }
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentFollowerBinding.inflate(inflater, container, false)
            return binding.root
        }
    private fun setUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}