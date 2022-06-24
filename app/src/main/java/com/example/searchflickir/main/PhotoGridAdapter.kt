package com.example.searchflickir.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchflickir.databinding.GridViewItemBinding
import com.example.searchflickir.network.ImageData

class PhotoGridAdapter : ListAdapter<ImageData,
        PhotoGridAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.ViewHolder {
        return ViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    inner class ViewHolder(private var binding:
                                    GridViewItemBinding
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: ImageData) {
            Log.d("ViewHolder", "Item exist")
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.getImageUrl() == newItem.getImageUrl()
        }
    }
}