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
import com.example.mob_calculator_4.utils.calculateShortCircuitCurrent

@Composable
fun SinglePhaseCalcScreen() {
    var pKz by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = pKz,
            onValueChange = { pKz = it },
            label = { Text("Введіть потужність КЗ (кВт)") }
        )
        Button(onClick = {
            val pKzValue = pKz.toDoubleOrNull()
            if (pKzValue != null) {
                result = calculateShortCircuitCurrent(pKzValue)
            }
        }) {
            Text("Розрахувати")
        }
        Text("Результат: ${result ?: "—"} кА")
    }
}
