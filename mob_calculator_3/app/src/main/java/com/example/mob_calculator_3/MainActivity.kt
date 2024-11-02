package com.example.mob_calculator_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mob_calculator_3.ui.theme.Mob_calculator_3Theme
import kotlin.math.pow
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.exp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mob_calculator_3Theme {
                SolarPowerProfitCalculator()
            }
        }
    }
}

@Composable
fun SolarPowerProfitCalculator() {
    // Стани для введення даних
    var power by remember { mutableStateOf("5") }
    var deviationInitial by remember { mutableStateOf("1") }
    var deviationImproved by remember { mutableStateOf("0.25") }
    var costPerKWh by remember { mutableStateOf("7") }
    var result by remember { mutableStateOf("") }

    // Функція для обчислення прибутку
    fun calculateProfit() {
        val averageCapacity = power.toDoubleOrNull() ?: 0.0
        val sigma1 = deviationInitial.toDoubleOrNull() ?: 0.0
        val sigma2 = deviationImproved.toDoubleOrNull() ?: 0.0
        val cost = costPerKWh.toDoubleOrNull() ?: 0.0

        // Розрахунок частки енергії без небалансів для початкової та покращеної похибки
        val probabilityNoImbalance1 = calculateNormalProbability(5.0, sigma1, 4.75, 5.25)
        val probabilityNoImbalance2 = calculateNormalProbability(5.0, sigma2, 4.75, 5.25)

        // Відповідні обсяги енергії без небалансів та з небалансами
        val w1NoImbalance = averageCapacity * 24 * probabilityNoImbalance1
        val w1Imbalance = averageCapacity * 24 * (1 - probabilityNoImbalance1)
        val w2NoImbalance = averageCapacity * 24 * probabilityNoImbalance2
        val w2Imbalance = averageCapacity * 24 * (1 - probabilityNoImbalance2)

        // Обчислення початкового прибутку та штрафів
        val p1 = w1NoImbalance * cost
        val i1 = w1Imbalance * cost
        val profitInitial = p1 - i1

        // Обчислення прибутку після покращення
        val p2 = w2NoImbalance * cost
        val i2 = w2Imbalance * cost
        val profitImproved = p2 - i2

        // Результат
        result = "Прибуток після покращення: ${String.format("%.2f", profitImproved)} тис. грн"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            "Розрахунок прибутку від сонячної енергетики",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Поле для введення потужності
        OutlinedTextField(
            value = power,
            onValueChange = { power = it },
            label = { Text("Потужність (кВт)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Поле для введення початкової похибки
        OutlinedTextField(
            value = deviationInitial,
            onValueChange = { deviationInitial = it },
            label = { Text("Початкова похибка (%)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Поле для введення покращеної похибки
        OutlinedTextField(
            value = deviationImproved,
            onValueChange = { deviationImproved = it },
            label = { Text("Покращена похибка (%)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Поле для введення вартості електроенергії
        OutlinedTextField(
            value = costPerKWh,
            onValueChange = { costPerKWh = it },
            label = { Text("Вартість за кВт·год (грн)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для розрахунку
        Button(
            onClick = { calculateProfit() },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Text(
                "Розрахувати",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.2,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Виведення результату
        if (result.isNotEmpty()) {
            Text(
                result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}


// Функція для обчислення нормального розподілу в певному інтервалі
fun calculateNormalProbability(mean: Double, stdDev: Double, lower: Double, upper: Double): Double {
    fun normalPdf(x: Double, mean: Double, stdDev: Double): Double {
        return (1 / (stdDev * sqrt(2 * Math.PI))) * exp(-0.5 * ((x - mean) / stdDev).pow(2))
    }

    // Інтегруємо за допомогою числового методу (трапеції)
    val steps = 1000
    val stepSize = (upper - lower) / steps
    var area = 0.0
    for (i in 0 until steps) {
        val x1 = lower + i * stepSize
        val x2 = x1 + stepSize
        area += (normalPdf(x1, mean, stdDev) + normalPdf(x2, mean, stdDev)) / 2 * stepSize
    }
    return area
}

@Preview(showBackground = true)
@Composable
fun PreviewSolarPowerProfitCalculator() {
    SolarPowerProfitCalculator()
}
