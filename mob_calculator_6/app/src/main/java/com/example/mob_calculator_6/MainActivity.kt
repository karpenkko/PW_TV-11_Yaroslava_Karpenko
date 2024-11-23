package com.example.mob_calculator_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mob_calculator_6.ui.theme.Mob_calculator_6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mob_calculator_6Theme {
                CalculatorScreen()
            }
        }
    }
}