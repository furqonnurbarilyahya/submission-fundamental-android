package com.bangkit.submissionfundamentalsatu.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.databinding.ActivityMainBinding
import com.bangkit.submissionfundamentalsatu.ui.viewModel.MainViewModel
import com.bangkit.submissionfundamentalsatu.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

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