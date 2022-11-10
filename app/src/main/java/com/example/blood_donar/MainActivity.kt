package com.example.blood_donar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var _searchDonar : Button
    private lateinit var _becomeDonar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _searchDonar = findViewById(R.id.searchDonar)
        _searchDonar.setOnClickListener {
            Log.i("donar", "you clicked search donar button")
        }
    }
}