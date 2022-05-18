package com.example.npod.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.npod.R
import com.example.npod.databinding.FragmentSettingsBinding
import com.example.npod.ui.MainActivity

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSettingsBinding.bind(view)

        activity?.let { activity ->
            val preferences = activity.getPreferences(Context.MODE_PRIVATE)
            val checkedButtonId = when (preferences.getInt(
                MainActivity.PREFERENCES_THEME_KEY,
                R.style.BaseTheme_DefaultTheme
            )) {
                R.style.BaseTheme_DefaultTheme -> R.id.rb_theme_default
                R.style.BaseTheme_MarsTheme -> R.id.rb_theme_mars
                R.style.BaseTheme_DarkTheme -> R.id.rb_theme_dark
                else -> R.id.rb_theme_default
            }
            binding.themesRadioGroup.check(checkedButtonId)
            binding.themesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rb_theme_default -> {
                        preferences.edit()
                            .putInt(
                                MainActivity.PREFERENCES_THEME_KEY,
                                R.style.BaseTheme_DefaultTheme
                            )
                            .apply()
                    }
                    R.id.rb_theme_mars -> {
                        preferences.edit()
                            .putInt(MainActivity.PREFERENCES_THEME_KEY, R.style.BaseTheme_MarsTheme)
                            .apply()
                    }
                    R.id.rb_theme_dark -> {
                        preferences.edit()
                            .putInt(MainActivity.PREFERENCES_THEME_KEY, R.style.BaseTheme_DarkTheme)
                            .apply()
                    }
                }
            }

            binding.btnSave.setOnClickListener {
                activity.recreate()
            }
        }

    }
}