package com.bangkit.submissionfundamentalsatu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.submissionfundamentalsatu.data.response.ItemsItem
import com.bangkit.submissionfundamentalsatu.databinding.ItemRowUserBinding
import com.bangkit.submissionfundamentalsatu.ui.activity.DetailUserActivity
import com.bumptech.glide.Glide

class UserAdapter: ListAdapter<ItemsItem, UserAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (user: ItemsItem) {
            binding.tvUsername.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.KEY_USER, user.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}