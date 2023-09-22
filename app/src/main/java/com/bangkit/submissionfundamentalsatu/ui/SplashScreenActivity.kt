package com.bangkit.submissionfundamentalsatu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkit.submissionfundamentalsatu.R

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashScreenDuration = 2000

        Handler().postDelayed({
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration.toLong())

    }
}