package com.example.mob_calculator_6

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = remember { CalculatorViewModel() }) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        EquipmentTable(viewModel.equipmentList)
        CalculateButton(viewModel)
        CalculationResults(viewModel.calculationState)
        CoefficientInputs(viewModel)
        TransformerResults(viewModel.calculationState.transformerCalculations)
    }
}

@Composable
private fun EquipmentTable(equipmentList: List<Equipment>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp
    ) {
        Column {
            equipmentList.forEach { equipment ->
                EquipmentParameterRow("Найменування ЕП", equipment.name) { equipment.name = it }
                EquipmentParameterRow("Номінальне значення ККД", equipment.efficiency) { equipment.efficiency = it }
                EquipmentParameterRow("Коефіцієнт потужності", equipment.powerFactor) { equipment.powerFactor = it }
                EquipmentParameterRow("Напруга навантаження", equipment.voltage) { equipment.voltage = it }
                EquipmentParameterRow("Кількість ЕП", equipment.quantity) { equipment.quantity = it }
                EquipmentParameterRow("Номінальна потужність", equipment.nominalPower) { equipment.nominalPower = it }
                EquipmentParameterRow("Коефіцієнт використання", equipment.utilizationFactor) { equipment.utilizationFactor = it }
                EquipmentParameterRow("Коефіцієнт реактивної потужності", equipment.reactivePowerFactor) { equipment.reactivePowerFactor = it }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
private fun EquipmentParameterRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1.5f)
                .padding(8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun CalculateButton(viewModel: CalculatorViewModel) {
    Button(
        onClick = { viewModel.calculate() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("Обчислити")
    }
}

@Composable
private fun CalculationResults(state: CalculationState) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        ResultText("Груповий коефіцієнт використання", state.groupUtilizationCoefficient)
        ResultText("Ефективна кількість ЕП", state.effectiveEpAmount)
        ResultText("Розрахункове активне навантаження", state.calculatedPower.activeLoad)
        ResultText("Розрахункове реактивне навантаження", state.calculatedPower.reactiveLoad)
        ResultText("Повна потужність", state.calculatedPower.fullPower)
        ResultText("Розрахунковий груповий струм", state.calculatedPower.groupCurrent)
    }
}

@Composable
private fun CoefficientInputs(viewModel: CalculatorViewModel) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            value = viewModel.activeCoefficient,
            onValueChange = { viewModel.activeCoefficient = it },
            label = { Text("Розрахунковий коефіцієнт активної потужності") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.transformerCoefficient,
            onValueChange = { viewModel.transformerCoefficient = it },
            label = { Text("Коефіцієнт трансформатора") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TransformerResults(calculations: TransformerCalculations) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        ResultText("Активне навантаження на шинах 0,38 кВ ТП", calculations.activeLoad)
        ResultText("Реактивне навантаження на шинах 0,38 кВ ТП", calculations.reactiveLoad)
        ResultText("Повна потужність на шинах 0,38 кВ ТП", calculations.fullPower)
        ResultText("Розрахунковий груповий струм на шинах 0,38 кВ ТП", calculations.groupCurrent)
    }
}

@Composable
private fun ResultText(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.8f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.4f)
        )
    }
}
