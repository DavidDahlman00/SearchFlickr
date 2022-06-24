package com.example.searchflickir.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchflickir.databinding.GridViewItemBinding
import com.example.searchflickir.extraFragments.ImageFragment
import com.example.searchflickir.network.ImageData

class PhotoGridAdapter(fragment : Fragment) : ListAdapter<ImageData,
        PhotoGridAdapter.ViewHolder>(DiffCallback) {

    val fragment = fragment

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

        init{
            binding.flickrGridImage.setOnClickListener {
                openImageDialog()
                Log.d("open Image", "????")
            }
        }

        fun bind(photo: ImageData) {
            Log.d("ViewHolder", "Item exist")
            binding.photo = photo
            binding.executePendingBindings()
        }

        private fun openImageDialog(){
            val imageDialog = ImageFragment()
            imageDialog.show(fragment.parentFragmentManager, "imageDialog")
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