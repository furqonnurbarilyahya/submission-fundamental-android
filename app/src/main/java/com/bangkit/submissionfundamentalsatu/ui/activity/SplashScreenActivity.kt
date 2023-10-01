package com.bangkit.submissionfundamentalsatu.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.submissionfundamentalsatu.R
import com.bangkit.submissionfundamentalsatu.ui.viewModel.SettingViewModel
import com.bangkit.submissionfundamentalsatu.ui.viewModel.ViewModelFactory

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private val settingViewModel by viewModels<SettingViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var themeNow = false

    companion object {
        const val DURATION = 2000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DURATION.toLong())

        settingViewModel.getThemeSetting().observe(this) {theme ->
            themeNow = theme
            val currentTheme = if (theme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(currentTheme)
        }

    }
}