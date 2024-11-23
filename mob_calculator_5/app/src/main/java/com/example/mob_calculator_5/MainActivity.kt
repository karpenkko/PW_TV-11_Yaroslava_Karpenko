package com.example.mob_calculator_5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mob_calculator_5.screens.MainScreen
import com.example.mob_calculator_5.screens.ReliabilityCalculationScreen
import com.example.mob_calculator_5.screens.PowerLossCalculatorScreen
import com.example.mob_calculator_5.ui.theme.Mob_calculator_5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mob_calculator_5Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") { MainScreen(navController) }
            composable("reliabilityScreen") { ReliabilityCalculationScreen() }
            composable("lossCalculationScreen") { PowerLossCalculatorScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Mob_calculator_5Theme {
        MyApp()
    }
}
