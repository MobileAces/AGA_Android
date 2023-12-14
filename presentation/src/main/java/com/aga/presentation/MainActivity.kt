package com.aga.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.steelzoo.presentation.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frame = findViewById<FrameLayout>(R.id.fl_main)
        supportFragmentManager.beginTransaction().replace(R.id.fl_main, StatisticsFragment()).commit()
    }
}