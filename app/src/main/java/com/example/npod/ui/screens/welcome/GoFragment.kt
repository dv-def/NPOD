package com.example.npod.ui.screens.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.npod.R
import com.example.npod.databinding.FragmentGoBinding

class GoFragment : Fragment(R.layout.fragment_go) {
    private val welcomeViewModel by viewModels<WelcomeViewModel>({ requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentGoBinding.bind(view)
        binding.btnGo.setOnClickListener {
            welcomeViewModel.goToApp()
        }
    }

    override fun onResume() {
        super.onResume()
        welcomeViewModel.cantSkip()
    }
}