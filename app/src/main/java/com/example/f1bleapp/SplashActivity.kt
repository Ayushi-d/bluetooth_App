package com.example.f1bleapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        Handler().postDelayed(Runnable { // This method will be executed once the timer is over
            val i = Intent(this@SplashActivity, MainActivity2::class.java)
            startActivity(i)
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this)
            val editor1 = sharedPreferences1.edit()
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            editor1.clear()
            editor1.apply()
            finish()
        }, 5000)
    }
}