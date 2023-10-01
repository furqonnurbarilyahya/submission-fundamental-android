package com.bangkit.submissionfundamentalsatu.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.databinding.ActivityMainBinding
import com.bangkit.submissionfundamentalsatu.ui.viewModel.MainViewModel
import com.bangkit.submissionfundamentalsatu.adapter.UserAdapter
import com.bangkit.submissionfundamentalsatu.ui.viewModel.SettingViewModel
import com.bangkit.submissionfundamentalsatu.ui.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val settingViewModel by viewModels<SettingViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var themeNow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.user.observe(this){ userList->
            setUserData(userList)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.fabFavorite.setOnClickListener{
            val favoriteIntent = Intent(this, FavoriteUserActivity::class.java)
            startActivity(favoriteIntent)
        }

        binding.fabSettings.setOnClickListener{
            val settingIntent = Intent(this, SettingActivity::class.java)
            startActivity(settingIntent)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    searchBar.text  = searchView.text
                    searchView.hide()
                    mainViewModel.searchUser(searchView.text.toString())
                    false
                }
        }

        settingViewModel.getThemeSetting().observe(this) {theme ->
            themeNow = theme
            val currentTheme = if (theme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(currentTheme)
        }

    }


    private fun setUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}