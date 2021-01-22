package com.bozhanova.teleprompter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    companion object {
        internal const val SCROLL_FUCK :String = "com.bozhanova.acton.ONSCROLL"
    }
}