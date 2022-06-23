package com.example.searchflickir

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.searchflickir.main.FlickrApiStatus


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