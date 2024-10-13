package com.example.mob_calculator_2

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
import com.example.mob_calculator_2.ui.theme.Mob_calculator_2Theme
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mob_calculator_2Theme {
                EmissionCalculatorScreen() // Запуск головного екрану для розрахунків
            }
        }
    }
}

@Composable
fun EmissionCalculatorScreen() {
    // Змінні для введення значень користувачем
    var coalCombustion by remember { mutableStateOf("") } // Кількість тепла при згорянні вугілля
    var fuelOilCombustion by remember { mutableStateOf("") } // Кількість тепла при згорянні мазуту
    var coalAshContent by remember { mutableStateOf("") } // Вміст золи у вугіллі
    var fuelOilAshContent by remember { mutableStateOf("") } // Вміст золи в мазуті
    var coalFuelMass by remember { mutableStateOf("") } // Маса вугілля
    var fuelOilFuelMass by remember { mutableStateOf("") } // Маса мазуту
    var dustRemovalEfficiency by remember { mutableStateOf("") } // Ефективність золовловлення
    var results by remember { mutableStateOf(listOf<String>()) } // Список для результатів розрахунків

    // Основний макет
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
    ) {
        // Заголовок екрану
        Text("Розрахунок валового викиду твердих частинок", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(30.dp))

        // Поля для введення теплових значень
        Text(
            text = "Нижча теплота згоряння робочої маси:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = coalCombustion,
                onValueChange = { coalCombustion = it },
                label = { Text("Вугілля (МДж/кг)") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            OutlinedTextField(
                value = fuelOilCombustion,
                onValueChange = { fuelOilCombustion = it },
                label = { Text("Мазут (МДж/кг)") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Поля для введення зольності
        Text(
            text = "Масовий вміст золи в паливі:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = coalAshContent,
                onValueChange = { coalAshContent = it },
                label = { Text("Вугілля (%)") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            OutlinedTextField(
                value = fuelOilAshContent,
                onValueChange = { fuelOilAshContent = it },
                label = { Text("Мазут (%)") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Поля для введення маси палива
        Text(
            text = "Маса палива:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = coalFuelMass,
                onValueChange = { coalFuelMass = it },
                label = { Text("Вугілля (т)") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            OutlinedTextField(
                value = fuelOilFuelMass,
                onValueChange = { fuelOilFuelMass = it },
                label = { Text("Мазут (т)") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле для введення ефективності золовловлення
        OutlinedTextField(
            value = dustRemovalEfficiency,
            onValueChange = { dustRemovalEfficiency = it },
            label = { Text("Ефективність золовловлення") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для розрахунку
        Button(
            onClick = {
                // Виклик функції для розрахунку викидів для вугілля
                val (coalEmissionIndex, coalEmission) = calculateCoalEmissions(
                    coalCombustion.toDoubleOrNull() ?: 0.0,
                    coalAshContent.toDoubleOrNull() ?: 0.0,
                    coalFuelMass.toDoubleOrNull() ?: 0.0,
                    dustRemovalEfficiency.toDoubleOrNull() ?: 0.0
                )

                // Виклик функції для розрахунку викидів для мазуту
                val (fuelOilEmissionIndex, fuelOilEmission) = calculateFuelOilEmissions(
                    fuelOilCombustion.toDoubleOrNull() ?: 0.0,
                    fuelOilAshContent.toDoubleOrNull() ?: 0.0,
                    fuelOilFuelMass.toDoubleOrNull() ?: 0.0,
                    dustRemovalEfficiency.toDoubleOrNull() ?: 0.0
                )

                // Виведення результатів у списку
                results = listOf(
                    "Валовий викид вугілля: $coalEmissionIndex т.",
                    "Зольність вугілля: $coalEmission %",
                    "Валовий викид мазуту: $fuelOilEmissionIndex т.",
                    "Зольність мазуту: $fuelOilEmission %"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 16.dp),
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

        // Виведення результатів
        Column(modifier = Modifier.padding(top = 16.dp)) {
            results.forEach { result ->
                Text(result, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

// Функція для розрахунку валових викидів та індексу викидів для вугілля
fun calculateCoalEmissions(coalCombustion: Double, coalAshContent: Double, coalFuelMass: Double, dustRemovalEfficiency: Double): Pair<Double, Double> {
    // Розрахунок індексу викидів
    val emissionIndex = 10.0.pow(6) / coalCombustion * 0.8 * coalAshContent / (100 - 1.5) * (1 - dustRemovalEfficiency)

    // Розрахунок валових викидів
    val emission = 10.0.pow(-6) * emissionIndex * coalCombustion * coalFuelMass

    // Округлення результатів до двох знаків після коми та повернення значень
    return Pair(
        (round(emissionIndex * 100) / 100),
        (round(emission * 100) / 100)
    )
}

// Функція для розрахунку валових викидів та індексу викидів для мазуту
fun calculateFuelOilEmissions(fuelOilCombustion: Double, fuelOilAshContent: Double, fuelOilFuelMass: Double, dustRemovalEfficiency: Double): Pair<Double, Double> {
    // Розрахунок індексу викидів
    val emissionIndex = 10.0.pow(6) / fuelOilCombustion * 1 * fuelOilAshContent / (100 - 1.5) * (1 - dustRemovalEfficiency)

    // Розрахунок валових викидів
    val emission = 10.0.pow(-6) * emissionIndex * fuelOilCombustion * fuelOilFuelMass

    // Округлення результатів до двох знаків після коми та повернення значень
    return Pair(
        (round(emissionIndex * 100) / 100),
        (round(emission * 100) / 100)
    )
}