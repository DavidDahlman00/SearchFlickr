package com.example.searchflickir

import android.graphics.PorterDuff
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.searchflickir.main.FlickrApiStatus
import com.example.searchflickir.main.PhotoGridAdapter
import com.example.searchflickir.network.ImageData


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            Log.d("Photo Url", imgUrl)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_no_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<ImageData>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}



@BindingAdapter("flickirApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: FlickrApiStatus?) {
    when (status) {
        FlickrApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        FlickrApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_no_internet)
        }
        FlickrApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}

