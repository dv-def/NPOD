package com.example.npod.ui.screens.welcome

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.npod.R

class MarsFragment : Fragment(R.layout.fragment_mars) {
    private val welcomeViewModel by viewModels<WelcomeViewModel>({ requireActivity() })

    override fun onResume() {
        super.onResume()
        welcomeViewModel.canSkip()
    }
}