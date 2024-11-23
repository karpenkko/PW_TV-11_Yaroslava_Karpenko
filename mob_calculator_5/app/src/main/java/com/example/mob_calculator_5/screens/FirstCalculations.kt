package com.example.mob_calculator_5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mob_calculator_5.utils.calculateReliabilitySingleLineSystem
import com.example.mob_calculator_5.utils.calculateReliabilityDoubleLineSystem

@Composable
fun ReliabilityCalculationScreen() {
    var singleLineReliability by remember { mutableStateOf(0.0) }
    var doubleLineReliability by remember { mutableStateOf(0.0) }
    var moreReliableSystem by remember { mutableStateOf("") }

    var lineLength by remember { mutableStateOf("") }
    var connectionCount by remember { mutableStateOf("") }

    fun calculateResults() {
        val length = lineLength.toDoubleOrNull() ?: 0.0
        val connections = connectionCount.toIntOrNull() ?: 0

        singleLineReliability = calculateReliabilitySingleLineSystem()
        doubleLineReliability = calculateReliabilityDoubleLineSystem()

        moreReliableSystem = if (singleLineReliability > doubleLineReliability) {
            "Одноколова система більш надійна."
        } else {
            "Двоколова система більш надійна."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Розрахунок надійності систем", fontSize = 20.sp)

        OutlinedTextField(
            value = lineLength,
            onValueChange = { lineLength = it },
            label = { Text("Довжина лінії (км)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = connectionCount,
            onValueChange = { connectionCount = it },
            label = { Text("Кількість приєднань") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { calculateResults() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Розрахувати", fontSize = 16.sp)
        }

        if (singleLineReliability != 0.0 && doubleLineReliability != 0.0) {
            Text("Надійність одноколової системи: %.6f".format(singleLineReliability))
            Text("Надійність двоколової системи: %.6f".format(doubleLineReliability))
            Text("Результат порівняння: $moreReliableSystem")
        }
    }
}