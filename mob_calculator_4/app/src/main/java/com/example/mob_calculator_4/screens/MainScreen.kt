package com.example.mob_calculator_4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("threePhaseCalc") }) {
            Text("Розрахунок трифазного КЗ")
        }
        Button(onClick = { navController.navigate("singlePhaseCalc") }) {
            Text("Розрахунок однофазного КЗ")
        }
        Button(onClick = { navController.navigate("stabilityCheck") }) {
            Text("Перевірка стійкості")
        }
    }
}
