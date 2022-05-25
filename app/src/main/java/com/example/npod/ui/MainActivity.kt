package com.example.npod.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.npod.R
import com.example.npod.databinding.ActivityMainBinding
import com.example.npod.ui.screens.MainFragment
import com.example.npod.ui.screens.SettingsFragment
import com.example.npod.ui.screens.WelcomeFragment
import com.example.npod.ui.screens.WikiSearchFragment
import com.example.npod.viewmodels.WelcomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val welcomeViewModel by viewModels<WelcomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLastTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            openFragment(
                fragment = WelcomeFragment(),
                addToBackStack = false
            )

            welcomeViewModel.inWelcomeContentLiveData.observe(this) { isOnWelcome ->
                if (isOnWelcome) {
                    binding.bottomNavigationView.visibility = View.GONE
                } else {
                    openFragment(
                        fragment = MainFragment(),
                        addToBackStack = false
                    )
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    openFragment(
                        fragment = MainFragment(),
                        addToBackStack = false
                    )
                }
                R.id.item_search -> {
                    openFragment(
                        fragment = WikiSearchFragment(),
                        addToBackStack = true
                    )
                }

                R.id.item_setting -> {
                    openFragment(
                        fragment = SettingsFragment(),
                        addToBackStack = true
                    )
                }
            }
            true
        }
    }

    private fun setLastTheme() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        var themeId = preferences.getInt(PREFERENCES_THEME_KEY, 0)

        if (themeId == 0) {
            themeId = R.style.BaseTheme_DefaultTheme
        }

        setTheme(themeId)
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (addToBackStack) {
                addToBackStack(null)
            }
                replace(R.id.fragment_container_view, fragment)
                commit()
        }
    }

    companion object {
        const val PREFERENCES_THEME_KEY = "PREFERENCES_THEME_KEY"
    }
}