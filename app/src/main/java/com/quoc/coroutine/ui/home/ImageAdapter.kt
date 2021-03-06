package com.quoc.coroutine.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quoc.coroutine.BuildConfig
import com.quoc.coroutine.databinding.ItemImageBinding
import com.quoc.coroutine.di.GlideRequests
import com.quoc.coroutine.domain.model.ImageModel

class ImageAdapter(
    private val glide: GlideRequests,
    private val action: (ImageModel) -> Unit
) : ListAdapter<ImageModel, ImageAdapter.ImageViewHolder>(
    object : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.author == newItem.author
                    && oldItem.downloadUrl == newItem.downloadUrl
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, glide)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
        holder.itemView.setOnClickListener {
            action.invoke(item)
        }
    }

    class ImageViewHolder(private val binding: ItemImageBinding, val glide: GlideRequests) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageModel) {
            val url = item.getThumbnailUrl(BuildConfig.BASE_URL)
            if (URLUtil.isNetworkUrl(url)) {
                glide.load(url)
                    .into(binding.ivImage)
            } else {
                glide.clear(binding.ivImage)
            }
            binding.tvAuthor.text = item.author
        }
    }
}