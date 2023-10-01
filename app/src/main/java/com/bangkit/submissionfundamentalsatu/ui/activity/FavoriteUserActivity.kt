package com.bangkit.submissionfundamentalsatu.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalsatu.adapter.UserAdapter
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.databinding.ActivityFavoriteUserBinding
import com.bangkit.submissionfundamentalsatu.ui.viewModel.FavoriteUserViewModel
import com.bangkit.submissionfundamentalsatu.ui.viewModel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel>{
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.layoutManager = layoutManager

        favoriteUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteUserViewModel.getFavoriteuser().observe(this) { favoriteUser ->
            val items = arrayListOf<ItemsItem>()
            favoriteUser.map {
                val item = ItemsItem(login = it.username!!, avatarUrl = it.avatarUrl!!)
                items.add(item)
            }
            setFavoriteUserData(items)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFavoriteUserData(favoriteUserData: ArrayList<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(favoriteUserData)
        binding.rvFavoriteUser.adapter = adapter
    }
}