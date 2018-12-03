package com.syllogismobile.sample_android.frontend.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.syllogismobile.sample_android.R
import com.syllogismobile.sample_android.frontend.HolderActivity

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        Handler().postDelayed({ startActivity(Intent(this, HolderActivity::class.java)) }, 2000)
    }
}