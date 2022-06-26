package com.example.searchflickir.extraFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentBottomSheetSettingsBinding
import com.example.searchflickir.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetSettings(val viewModel: MainViewModel) : BottomSheetDialogFragment() {

    private var bottomSheetSettingsBinding : FragmentBottomSheetSettingsBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_settings, container, false)
        val binding = FragmentBottomSheetSettingsBinding.bind(view)
        bottomSheetSettingsBinding = binding

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetSettingsBinding = null
    }
}