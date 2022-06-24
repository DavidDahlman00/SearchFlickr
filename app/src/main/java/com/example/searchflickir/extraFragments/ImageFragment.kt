package com.example.searchflickir.extraFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.searchflickir.R
import com.example.searchflickir.databinding.FragmentImageBinding
import com.example.searchflickir.main.MainViewModel


class ImageFragment : DialogFragment() {

    private var imageFragmentBinding: FragmentImageBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val binding = FragmentImageBinding.bind(view)
        imageFragmentBinding = binding
        imageFragmentBinding?.textView?.text = MainViewModel.focusedImage.title
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        imageFragmentBinding = null
    }
}