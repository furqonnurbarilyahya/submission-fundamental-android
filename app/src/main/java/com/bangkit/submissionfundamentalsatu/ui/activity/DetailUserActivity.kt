package com.bangkit.submissionfundamentalsatu.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.submissionfundamentalsatu.R
import com.bangkit.submissionfundamentalsatu.data.response.DetailUserResponse
import com.bangkit.submissionfundamentalsatu.databinding.ActivityDetailUserBinding
import com.bangkit.submissionfundamentalsatu.ui.viewModel.DetailViewModel
import com.bangkit.submissionfundamentalsatu.adapter.SectionsPagerAdapter
import com.bangkit.submissionfundamentalsatu.database.entity.UserEntity
import com.bangkit.submissionfundamentalsatu.ui.viewModel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var favoriteUser: UserEntity? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetail = intent.getStringExtra(KEY_USERNAME)
        val userAvatar = intent.getStringExtra(KEY_AVATAR)
        detailViewModel.detailUser(userDetail.toString())
        detailViewModel.detailUser.observe(this) { user ->
            setDetailUser(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isFavoriteUser(userDetail.toString()).observe(this) { favuser ->
            setFavoriteUser(favuser)
            isFavorite = favuser
        }

        favoriteUser.let {
            favoriteUser?.username.toString()
            favoriteUser?.avatarUrl.toString()
        }

        binding.fabAddFavorite.setOnClickListener{
                    if (isFavorite) {
                        setFavoriteUser(false)
                        detailViewModel.deleteFavoriteUser(userDetail.toString())
                    } else {
                        setFavoriteUser(true)
                        favoriteUser = UserEntity(
                            username = userDetail.toString(),
                            avatarUrl = userAvatar.toString()
                        )
                        detailViewModel.insertFavoriteUser(favoriteUser as UserEntity)
                    }
                }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userDetail!!
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setDetailUser (detailUserResponse: DetailUserResponse) {
        binding.tvUsernameDetail.text = detailUserResponse.name
        binding.tvUsernameDetail2.text = detailUserResponse.login
        binding.tvDtFollower.text = detailUserResponse.followers.toString().trim()
        binding.tvDtFollowing.text = detailUserResponse.following.toString().trim()
        Glide.with(binding.root.context)
            .load(detailUserResponse.avatarUrl)
            .into(binding.imgDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvFollower.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.tvFollowing.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.tabs.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.fabAddFavorite.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun setFavoriteUser(favoriteUser: Boolean) {
        if (favoriteUser) {
            binding.fabAddFavorite.setImageResource(R.drawable.ic_favorite_fullsolid)
        } else {
            binding.fabAddFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val KEY_USERNAME = "key_user"
        const val KEY_AVATAR = "key_avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}