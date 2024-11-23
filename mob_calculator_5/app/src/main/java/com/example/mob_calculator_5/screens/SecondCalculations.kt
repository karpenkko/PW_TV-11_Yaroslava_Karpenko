package com.example.mob_calculator_5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mob_calculator_5.utils.calculatePowerLoss

@Composable
fun PowerLossCalculatorScreen() {
    var emergencyLossRate by remember { mutableStateOf("") }
    var plannedLossRate by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Розрахунок збитків від вимкнень", fontSize = 20.sp)

        OutlinedTextField(
            value = emergencyLossRate,
            onValueChange = { emergencyLossRate = it },
            label = { Text("Питомі збитки аварійних вимкнень (грн/кВт·год)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = plannedLossRate,
            onValueChange = { plannedLossRate = it },
            label = { Text("Питомі збитки планових вимкнень (грн/кВт·год)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val emergencyRate = emergencyLossRate.toDoubleOrNull()
                val plannedRate = plannedLossRate.toDoubleOrNull()

                if (emergencyRate != null && plannedRate != null) {
                    result = calculatePowerLoss(emergencyRate, plannedRate)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Розрахувати", fontSize = 16.sp)
        }

        result?.let {
            Text("Збитки: %.2f грн".format(it))
        }
    }
}


