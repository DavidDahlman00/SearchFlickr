package com.example.searchflickir.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentMainBinding
import com.example.searchflickir.extraFragments.BottomSheetSettings
import com.example.searchflickir.extraFragments.ImageFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val settings = BottomSheetSettings(viewModel)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(this)

        binding.mainSearchBtn.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(R.color.colorAccent, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }

        binding.mainMenuBtn.setOnClickListener {
            fragmentManager?.let { it1 -> settings.show(it1,"settings_bottom_sheet") }
        }

        binding.mainSearchBtn.setOnClickListener {
            viewModel.searchForNewPhotos("fish")
        }



        return binding.root
    }
}