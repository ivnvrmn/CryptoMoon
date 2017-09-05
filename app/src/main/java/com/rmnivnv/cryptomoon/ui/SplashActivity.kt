package com.rmnivnv.cryptomoon.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rmnivnv.cryptomoon.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
