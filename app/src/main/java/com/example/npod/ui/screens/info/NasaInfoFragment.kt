package com.example.npod.ui.screens.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.npod.R
import com.example.npod.ui.MainActivity

class NasaInfoFragment : Fragment(R.layout.fragment_nasa_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).supportActionBar?.hide()
    }
}