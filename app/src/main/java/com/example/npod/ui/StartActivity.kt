package com.example.npod.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.npod.R

class StartActivity : AppCompatActivity() {
    private var isShow = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        splash.setKeepOnScreenCondition {
            isShow
        }

        Handler(Looper.getMainLooper()).postDelayed({
            isShow = false
            finish()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        },2000L)
    }
}