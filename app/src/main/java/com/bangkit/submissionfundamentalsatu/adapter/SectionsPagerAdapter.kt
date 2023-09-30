package com.bangkit.submissionfundamentalsatu.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.submissionfundamentalsatu.ui.activity.FollowerFollowingFragment

class SectionsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFollowingFragment.ARG_POSITON, position + 1)
            putString(FollowerFollowingFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}