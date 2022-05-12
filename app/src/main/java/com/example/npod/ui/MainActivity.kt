package com.example.npod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.npod.R
import com.example.npod.databinding.ActivityMainBinding
import com.example.npod.ui.screens.MainFragment
import com.example.npod.ui.screens.WikiSearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, MainFragment())
                        .commit()

                    true
                }
                R.id.item_search -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, WikiSearchFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> true
            }
        }
    }
}