package com.quoc.coroutine.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoc.coroutine.databinding.ItemNetworkStateBinding

class LoadingAdapter : ListAdapter<Boolean, NetworkStateViewHolder>(
    object : DiffUtil.ItemCallback<Boolean>() {
        override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkStateViewHolder {
        return NetworkStateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NetworkStateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NetworkStateViewHolder(binding: ItemNetworkStateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Boolean) {
        itemView.isVisible = item
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val binding = ItemNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NetworkStateViewHolder(binding)
        }
    }
}
