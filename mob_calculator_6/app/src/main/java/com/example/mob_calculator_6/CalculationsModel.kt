package com.example.mob_calculator_6

data class CalculationState(
    val groupUtilizationCoefficient: Double = 0.0,
    val effectiveEpAmount: Double = 0.0,
    val calculatedPower: PowerCalculations = PowerCalculations(),
    val departmentCalculations: DepartmentCalculations = DepartmentCalculations(),
    val transformerCalculations: TransformerCalculations = TransformerCalculations()
)

data class PowerCalculations(
    val activeLoad: Double = 0.0,
    val reactiveLoad: Double = 0.0,
    val fullPower: Double = 0.0,
    val groupCurrent: Double = 0.0
)

data class DepartmentCalculations(
    val utilizationCoefficient: Double = 0.0,
    val effectiveAmount: Double = 0.0
)

data class TransformerCalculations(
    val activeLoad: Double = 0.0,
    val reactiveLoad: Double = 0.0,
    val fullPower: Double = 0.0,
    val groupCurrent: Double = 0.0
)