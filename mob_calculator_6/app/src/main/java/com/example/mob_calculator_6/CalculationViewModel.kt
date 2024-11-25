package com.example.mob_calculator_6

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.ceil
import kotlin.math.sqrt

class CalculatorViewModel {
    var equipmentList by mutableStateOf(getInitialEquipmentList())
    var calculationState by mutableStateOf(CalculationState())
    var activeCoefficient by mutableStateOf("1.25")
    var transformerCoefficient by mutableStateOf("0.7")

    private fun calculateEquipmentParameters() {
        var sumOfPowerProduct = 0.0
        var sumOfPowerUtilProduct = 0.0
        var sumOfSquaredPowerProduct = 0.0

        equipmentList.forEach { equipment ->
            val quantity = equipment.quantity.toDoubleOrNull() ?: 0.0
            val power = equipment.nominalPower.toDoubleOrNull() ?: 0.0
            val totalPower = quantity * power

            equipment.totalPower = totalPower.toString()
            equipment.current = calculateCurrent(
                totalPower,
                equipment.voltage.toDoubleOrNull() ?: 0.38,
                equipment.powerFactor.toDoubleOrNull() ?: 0.9,
                equipment.efficiency.toDoubleOrNull() ?: 0.92
            ).toString()

            sumOfPowerProduct += totalPower
            sumOfPowerUtilProduct += totalPower * (equipment.utilizationFactor.toDoubleOrNull() ?: 0.0)
            sumOfSquaredPowerProduct += quantity * power * power
        }

        updateCalculationState(sumOfPowerProduct, sumOfPowerUtilProduct, sumOfSquaredPowerProduct)
    }

    private fun calculateCurrent(power: Double, voltage: Double, powerFactor: Double, efficiency: Double): Double {
        return roundToTwoDecimalPlaces(power / (sqrt(3.0) * voltage * powerFactor * efficiency))
    }

    private fun updateCalculationState(
        totalPower: Double,
        totalPowerUtil: Double,
        totalSquaredPower: Double
    ) {
        val groupUtilCoef = roundToTwoDecimalPlaces(totalPowerUtil / totalPower)
        val effectiveAmount = roundToTwoDecimalPlaces(ceil((totalPower * totalPower) / totalSquaredPower))

        val activeCoef = activeCoefficient.toDoubleOrNull() ?: 1.25
        val voltage = 0.38
        val nominalPower = 23.0
        val tangentPhi = 1.58

        val activeLoad = roundToTwoDecimalPlaces(activeCoef * totalPowerUtil)
        val reactiveLoad = roundToTwoDecimalPlaces(groupUtilCoef * nominalPower * tangentPhi)
        val fullPower = roundToTwoDecimalPlaces(sqrt(activeLoad * activeLoad + reactiveLoad * reactiveLoad))
        val groupCurrent = roundToTwoDecimalPlaces(activeLoad / voltage)

        calculationState = calculationState.copy(
            groupUtilizationCoefficient = groupUtilCoef,
            effectiveEpAmount = effectiveAmount,
            calculatedPower = PowerCalculations(
                activeLoad = activeLoad,
                reactiveLoad = reactiveLoad,
                fullPower = fullPower,
                groupCurrent = groupCurrent
            ),
            departmentCalculations = DepartmentCalculations(
                utilizationCoefficient = 752.0 / 2330.0,
                effectiveAmount = 2330.0 * 2330.0 / 96399.0
            )
        )

        calculateTransformerParameters()
    }

    private fun calculateTransformerParameters() {
        val coefficient = transformerCoefficient.toDoubleOrNull() ?: 0.7
        val activeLoad = roundToTwoDecimalPlaces(coefficient * 752.0)
        val reactiveLoad = roundToTwoDecimalPlaces(coefficient * 657.0)
        val fullPower = roundToTwoDecimalPlaces(sqrt(activeLoad * activeLoad + reactiveLoad * reactiveLoad))
        val groupCurrent = roundToTwoDecimalPlaces(activeLoad / 0.38)

        calculationState = calculationState.copy(
            transformerCalculations = TransformerCalculations(
                activeLoad = activeLoad,
                reactiveLoad = reactiveLoad,
                fullPower = fullPower,
                groupCurrent = groupCurrent
            )
        )
    }

    fun calculate() {
        calculateEquipmentParameters()
    }

    private fun roundToTwoDecimalPlaces(value: Double): Double {
        return String.format("%.2f", value).toDouble()
    }
}
