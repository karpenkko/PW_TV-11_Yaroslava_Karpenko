package com.example.mob_calculator_4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mob_calculator_4.utils.calculateCableParameters

@Composable
fun ThreePhaseCalcScreen() {
    var voltage by remember { mutableStateOf("") }
    var economicSection by remember { mutableStateOf<Double?>(null) }
    var thermalSection by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = voltage,
            onValueChange = { voltage = it },
            label = { Text("Введіть напругу (кВ)") }
        )
        Button(onClick = {
            val voltageValue = voltage.toDoubleOrNull()
            if (voltageValue != null) {
                val result = calculateCableParameters(voltageValue)
                economicSection = result.first
                thermalSection = result.second
            }
        }) {
            Text("Розрахувати")
        }
        Text("Економічний переріз: ${economicSection?.let { "%.2f мм²".format(it) } ?: "—"}")
        Text("Термічний переріз: ${thermalSection?.let { "%.2f мм²".format(it) } ?: "—"}")
    }
}

