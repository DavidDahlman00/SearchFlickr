package com.example.searchflickir.main

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentMainBinding
import com.example.searchflickir.extraFragments.BottomSheetSettings
import com.example.searchflickir.extraFragments.ErrorDialogFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            parentFragmentManager.let { it1 -> settings.show(it1,"settings_bottom_sheet") }
        }

        binding.mainSearchBtn.setOnClickListener {
            val searchInput = binding.mainSearchText.text.toString().lowercase()
            viewModel.searchForNewPhotos(searchInput)
            Log.d("latlong", "lat: ${MainViewModel.latitude}, long: ${MainViewModel.longitude}")
            Log.d("latlong", "on : ${MainViewModel.useLocation}")
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
            Log.d("status", status.toString())
            if (status == FlickrApiStatus.ERROR) {
                val errorDialogFragment = ErrorDialogFragment(viewModel.status.value.toString())
                errorDialogFragment.show(parentFragmentManager, "errorDialogFragment")
            }

        }
        return binding.root
    }
}