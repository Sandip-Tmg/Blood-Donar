package com.example.blood_donar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var _searchDonar : Button
    private lateinit var _becomeDonar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _searchDonar = findViewById(R.id.searchDonar)
        _becomeDonar = findViewById(R.id.becomeDonar)
        _searchDonar.setOnClickListener {
            startSearchDonar()
        }
        _becomeDonar.setOnClickListener {
            startBecomeDonar()
        }

    }

    private fun startSearchDonar(){
        var intent: Intent = Intent(this, DonarSearch::class.java)
        startActivity(intent)
    }

    private fun startBecomeDonar(){
        var intent : Intent = Intent(this, DonarSignUp::class.java)
        startActivity(intent)
    }
}