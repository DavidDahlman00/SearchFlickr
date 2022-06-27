package com.example.searchflickir.extraFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentBottomSheetSettingsBinding
import com.example.searchflickir.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BottomSheetSettings(val viewModel: MainViewModel) : BottomSheetDialogFragment() {

    private var bottomSheetSettingsBinding : FragmentBottomSheetSettingsBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_settings, container, false)
        val binding = FragmentBottomSheetSettingsBinding.bind(view)
        bottomSheetSettingsBinding = binding

        bottomSheetSettingsBinding?.settingsNumImagesRadioBtn1?.setOnClickListener {
            MainViewModel.numImagesToReturn = "20"
        }

        bottomSheetSettingsBinding?.settingsNumImagesRadioBtn2?.setOnClickListener {
            MainViewModel.numImagesToReturn = "100"
        }

        bottomSheetSettingsBinding?.settingsCreatedAfterRadioBtn1?.setOnClickListener {
            MainViewModel.minUploadDate = "1970-01-01"
        }

        bottomSheetSettingsBinding?.settingsCreatedAfterRadioBtn2?.setOnClickListener {
            val selectedTime = LocalDateTime.now().minusMonths(12)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = selectedTime.format(formatter)
            MainViewModel.minUploadDate = formatted
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetSettingsBinding = null
    }
}