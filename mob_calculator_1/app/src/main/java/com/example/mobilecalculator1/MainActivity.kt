package com.example.mobilecalculator1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonCalculator1: Button = findViewById(R.id.button_calculator1)
        val buttonCalculator2: Button = findViewById(R.id.button_calculator2)

        buttonCalculator1.setOnClickListener {
            val intent = Intent(this, Calculator1Activity::class.java)
            startActivity(intent)
        }

        buttonCalculator2.setOnClickListener {
            val intent = Intent(this, Calculator2Activity::class.java)
            startActivity(intent)
        }
    }
}