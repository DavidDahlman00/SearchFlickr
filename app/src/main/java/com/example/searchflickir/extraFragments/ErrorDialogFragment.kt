package com.example.searchflickir.extraFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentErrorDialogBinding

class ErrorDialogFragment(_errorText: String) : DialogFragment() {

    private var errorDialogBinding: FragmentErrorDialogBinding?= null
    private val errorText = _errorText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error_dialog, container, false)
        val binding = FragmentErrorDialogBinding.bind(view)
        errorDialogBinding = binding

        errorDialogBinding?.errorDialogText?.text = errorText

        errorDialogBinding?.errorDialogCloseBtn?.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        errorDialogBinding = null
    }
}