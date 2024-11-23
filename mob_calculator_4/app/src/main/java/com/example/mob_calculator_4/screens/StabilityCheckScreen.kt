package com.example.mob_calculator_4.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.mob_calculator_4.utils.calculateShortCircuitCurrents

@Composable
fun StabilityCheckScreen() {
    var resistanceHigh by remember { mutableStateOf("") }
    var reactanceHigh by remember { mutableStateOf("") }
    var resistanceMedium by remember { mutableStateOf("") }
    var reactanceMedium by remember { mutableStateOf("") }

    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = resistanceHigh,
            onValueChange = { resistanceHigh = it },
            label = { Text("Опір високої сторони (Rh)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = reactanceHigh,
            onValueChange = { reactanceHigh = it },
            label = { Text("Реактивний опір високої сторони (Xh)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = resistanceMedium,
            onValueChange = { resistanceMedium = it },
            label = { Text("Опір середньої сторони (Rm)") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = reactanceMedium,
            onValueChange = { reactanceMedium = it },
            label = { Text("Реактивний опір середньої сторони (Xm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (resistanceHigh.isNotBlank() && reactanceHigh.isNotBlank() && resistanceMedium.isNotBlank() && reactanceMedium.isNotBlank()) {
                    val resistanceHighValue = resistanceHigh.toDouble()
                    val reactanceHighValue = reactanceHigh.toDouble()
                    val resistanceMediumValue = resistanceMedium.toDouble()
                    val reactanceMediumValue = reactanceMedium.toDouble()

                    result = calculateShortCircuitCurrents(
                        resistanceHighValue,
                        reactanceHighValue,
                        resistanceMediumValue,
                        reactanceMediumValue
                    )
                } else {
                    result = "Будь ласка, введіть всі значення."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = result)
    }
}

