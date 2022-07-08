package com.example.searchflickir.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.searchflickir.FlickrApplication
import com.example.searchflickir.databinding.FragmentMainBinding
import com.example.searchflickir.settings.BottomSheetSettings
import com.example.searchflickir.extraFragments.ErrorDialogFragment
import com.example.searchflickir.settings.SettingsViewModel
import com.example.searchflickir.settings.SettingsViewModelFactory

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModel.MainViewModelFactory()
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((requireActivity().application as FlickrApplication).repository)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val settings = BottomSheetSettings(mainViewModel, settingsViewModel)

        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        binding.photosGrid.adapter = PhotoGridAdapter(this)

        binding.mainMenuBtn.setOnClickListener {
            parentFragmentManager.let { it1 -> settings.show(it1,"settings_bottom_sheet") }
        }

        binding.mainSearchBtn.setOnClickListener {
            val searchInput = binding.mainSearchText.text.toString().lowercase()
            mainViewModel.searchForNewPhotos(searchInput)

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