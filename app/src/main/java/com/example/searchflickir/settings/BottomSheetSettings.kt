package com.example.searchflickir.settings

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.searchflickir.R
import com.example.searchflickir.data.Settings
import com.example.searchflickir.databinding.FragmentBottomSheetSettingsBinding
import com.example.searchflickir.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class BottomSheetSettings(val mainViewModel: MainViewModel, _settingsViewModel: SettingsViewModel) :
    BottomSheetDialogFragment(),
    LocationListener {

    private val settingsViewModel = _settingsViewModel
    private var bottomSheetSettingsBinding : FragmentBottomSheetSettingsBinding?=null
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var tmpMonthsAgo: String = monthsAgoToString(3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_settings, container, false)
        val binding = FragmentBottomSheetSettingsBinding.bind(view)
        bottomSheetSettingsBinding = binding

        settingsViewModel.settingsData.observe(this){
            Log.d("222", "useUploadDate" + it[0].useUploadDate)
            if (it[0].useUploadDate){
                bottomSheetSettingsBinding?.settingsCreatedAfterswitch?.text = "on"
                bottomSheetSettingsBinding?.settingsCreatedAfterSeekBar?.visibility = View.VISIBLE
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.visibility = View.VISIBLE

            }else{
                bottomSheetSettingsBinding?.settingsCreatedAfterswitch?.text = "off"
                bottomSheetSettingsBinding?.settingsCreatedAfterSeekBar?.visibility = View.INVISIBLE
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.visibility = View.INVISIBLE
               Log.d("222", settingsViewModel.settingsData.value.toString())
            }
        }

        bottomSheetSettingsBinding?.settingsNumImagesseekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //MainViewModel.numImagesToReturn = progress.toString()
                val count = settingsViewModel.settingsData.value!![0].count + 1
                val tmpState = Settings(numImagesToReturn = progress.toString(), version = 1, count = count)
                settingsViewModel.insert(tmpState)
                bottomSheetSettingsBinding?.settingsNumNumtext?.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        bottomSheetSettingsBinding?.settingsCreatedAfterswitch?.setOnClickListener {
            //SettingsViewModel.settings.useUploadDate
            if (settingsViewModel.settingsData.value?.get(0)?.useUploadDate == true){
                val count = settingsViewModel.settingsData.value!![0].count + 1
                val tmpState = settingsViewModel.settingsData.value!![0].copy(useUploadDate = false, count = count)//Settings(useUploadDate = false, version = 1, count = count)//SettingsViewModel.settings.copy(useLocation = false)
                Log.d("222", "222 $tmpState")
                settingsViewModel.insert(tmpState)
                //SettingsViewModel.settings.useUploadDate = false

                bottomSheetSettingsBinding?.settingsCreatedAfterswitch?.text = "off"
                bottomSheetSettingsBinding?.settingsCreatedAfterSeekBar?.visibility = View.INVISIBLE
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.visibility = View.INVISIBLE
            }else{
                val count = settingsViewModel.settingsData.value!![0].count + 1
                val tmpState = settingsViewModel.settingsData.value!![0].copy(useUploadDate = true, count = count)//SettingsViewModel.settings.copy(useLocation = false)
                Log.d("222", "222 $tmpState")
                settingsViewModel.insert(tmpState)
                Log.d("222", "should set uploadDate to true")
                Log.d("222", settingsViewModel.settingsData.value.toString())
                //MainViewModel.minUploadDate = tmpMonthsAgo
                //SettingsViewModel.settings.useUploadDate = true
                bottomSheetSettingsBinding?.settingsCreatedAfterswitch?.text = "on"
                bottomSheetSettingsBinding?.settingsCreatedAfterSeekBar?.visibility = View.VISIBLE
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.visibility = View.VISIBLE
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.text = tmpMonthsAgo
            }
        }

        bottomSheetSettingsBinding?.settingsCreatedAfterSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tmpMonthsAgo = monthsAgoToString(-progress)
                val count = settingsViewModel.settingsData.value!![0].count + 1
                val tmpState = settingsViewModel.settingsData.value!![0].copy(minUploadDate = monthsAgoToString(-progress), count = count)
                settingsViewModel.insert(tmpState)
                //MainViewModel.minUploadDate = monthsAgoToString(-progress)
                bottomSheetSettingsBinding?.settingsCreatedAfterText?.text = monthsAgoToString(-progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })


        bottomSheetSettingsBinding?.settingsLocationRadioBtn1?.setOnClickListener {
           // MainViewModel.useLocation = false
            val count = settingsViewModel.settingsData.value!![0].count + 1
            val tmpState = settingsViewModel.settingsData.value!![0].copy(useLocation = false, count = count)
            settingsViewModel.insert(tmpState)
        }

        bottomSheetSettingsBinding?.settingsLocationRadioBtn2?.setOnClickListener {
            getLocation()
        }

        return view
    }

    private fun monthsAgoToString(months: Int): String {
        val selectedTime = LocalDateTime.now().minusMonths(months.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return selectedTime.format(formatter)
    }

    private fun getLocation() {
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)

        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }
        val count = settingsViewModel.settingsData.value!![0].count + 1
        val tmpState = settingsViewModel.settingsData.value!![0].copy(useLocation = true, count = count)
        settingsViewModel.insert(tmpState)
        //MainViewModel.useLocation = true
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetSettingsBinding = null
    }

    override fun onLocationChanged(location: Location) {
        MainViewModel.latitude = ((location.latitude * 100).toInt() / 100.0)
        MainViewModel.longitude = ((location.longitude * 100).toInt() / 100.0)
    }

    @Deprecated("Deprecated in Java")
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
/*
 XML to be done for seekbar
 <Switch
                    android:id="@+id/settings_location_switch"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:minHeight="48dp"
                    android:text="off" />

                <SeekBar
                    android:id="@+id/settings_location_seekBar"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:visibility="invisible"
                    android:min="1"
                    android:max="20"
                    android:progress="3"/>

                <TextView
                    android:id="@+id/settings_location_text"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text=""
                    android:visibility="invisible"
                    android:textSize="14sp"
                    android:textStyle="bold"
                    android:layout_gravity="center"/>*/