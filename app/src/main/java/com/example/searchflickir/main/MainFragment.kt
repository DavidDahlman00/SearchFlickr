package com.example.searchflickir.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.searchflickir.databinding.FragmentMainBinding
import com.example.searchflickir.settings.BottomSheetSettings
import com.example.searchflickir.extraFragments.ErrorDialogFragment

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModel.MainViewModelFactory()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val settings = BottomSheetSettings(mainViewModel)

        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        binding.photosGrid.adapter = PhotoGridAdapter(this)

        binding.mainMenuBtn.setOnClickListener {
            parentFragmentManager.let { it1 -> settings.show(it1,"settings_bottom_sheet") }
        }

        binding.mainSearchBtn.setOnClickListener {
            val searchInput = binding.mainSearchText.text.toString().lowercase()
            mainViewModel.searchForNewPhotos(searchInput)
            Log.d("latlong", "lat: ${MainViewModel.latitude}, long: ${MainViewModel.longitude}")
            Log.d("latlong", "on : ${MainViewModel.useLocation}")
        }

        mainViewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
            Log.d("status", status.toString())
            if (status == FlickrApiStatus.ERROR) {
                val errorDialogFragment = ErrorDialogFragment(mainViewModel.status.value.toString())
                errorDialogFragment.show(parentFragmentManager, "errorDialogFragment")
            }

        }
        return binding.root
    }
}