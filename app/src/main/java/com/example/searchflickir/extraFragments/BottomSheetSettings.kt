package com.example.searchflickir.extraFragments

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentBottomSheetSettingsBinding
import com.example.searchflickir.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class BottomSheetSettings(val viewModel: MainViewModel) :
    BottomSheetDialogFragment(),
    LocationListener {

    private var bottomSheetSettingsBinding : FragmentBottomSheetSettingsBinding?=null
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2


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

        bottomSheetSettingsBinding?.settingsLocationRadioBtn1?.setOnClickListener {
            MainViewModel.useLocation = false
        }

        bottomSheetSettingsBinding?.settingsLocationRadioBtn2?.setOnClickListener {
            getLocation()
        }

        return view
    }

    private fun getLocation() {
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        MainViewModel.useLocation = true
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetSettingsBinding = null
    }

    override fun onLocationChanged(location: Location) {
        MainViewModel.latitude = ((location.latitude * 100).toInt() / 100.0)
        MainViewModel.longitude = ((location.longitude * 100).toInt() / 100.0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}