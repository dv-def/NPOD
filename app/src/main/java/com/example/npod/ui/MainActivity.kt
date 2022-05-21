package com.example.npod.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.npod.R
import com.example.npod.databinding.ActivityMainBinding
import com.example.npod.ui.screens.MainFragment
import com.example.npod.ui.screens.SettingsFragment
import com.example.npod.ui.screens.WikiSearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLastTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MainFragment())
                        .commit()
                }
                R.id.item_search -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, WikiSearchFragment())
                        .addToBackStack(null)
                        .commit()
                }

                R.id.item_setting -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, SettingsFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
            true
        }
    }

    private fun setLastTheme() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        var themeId = preferences.getInt(PREFERENCES_THEME_KEY, 0)

        if (themeId == 0) {
            themeId = R.style.BaseTheme_DarkTheme
        }

        setTheme(themeId)
    }

    companion object {
        const val PREFERENCES_THEME_KEY = "PREFERENCES_THEME_KEY"
    }
}