package com.example.npod.ui.screens.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.npod.R
import com.example.npod.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private val welcomeViewModel by viewModels<WelcomeViewModel>( {requireActivity()})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding =  FragmentWelcomeBinding.bind(view)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.btnSkip.setOnClickListener {
            welcomeViewModel.skipWelcome()
        }

        welcomeViewModel.canSkipWelcomeLiveData.observe(viewLifecycleOwner) { canSkip ->
            if (canSkip) {
                binding.btnSkip.visibility = View.VISIBLE
            } else {
                binding.btnSkip.visibility = View.GONE
            }
        }
    }

    private class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragmentsCount = 4

        override fun getItemCount(): Int {
            return fragmentsCount
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HelloFragment()
                1 -> EarthFragment()
                2 -> MarsFragment()
                else -> GoFragment()
            }
        }
    }
}