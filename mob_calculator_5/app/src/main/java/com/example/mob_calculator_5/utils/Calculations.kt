package com.example.mob_calculator_5.utils

// Функція для розрахунку надійності одноколової системи
fun calculateReliabilitySingleLineSystem(): Double {
    // Частоти відмов для кожного елемента
    val failureRates = listOf(0.01, 0.07, 0.015, 0.02, 0.03 * 6) // Вимикач, ПЛ, трансформатор, ввідний вимикач, 6 приєднань
    val failureRateSum = failureRates.sum() // Σλ = 0.295

    // Середня тривалість відновлення
    val meanRecoveryTime = (0.01 * 30 + 0.07 * 10 + 0.015 * 100 + 0.02 * 15 + 0.18 * 2) / failureRateSum // Tвідн. = 10.75 год

    // Коефіцієнт аварійного простою
    return (failureRateSum * meanRecoveryTime) / 8760 // Кав. = 3.6 × 10^-4
}

// Функція для розрахунку надійності двоколової системи
fun calculateReliabilityDoubleLineSystem(): Double {
    val failureRateSingleLine = 0.295 // Частота відмов одноколової системи

    // Частота одночасного відмовлення двох кіл
    val failureRateTwoLinesSimultaneous = 2 * failureRateSingleLine * (13.6 * 1e-4) + 5.89 * 1e-7 // λдв. = 36.9 × 10^-4

    // Частота відмов двоколової системи з урахуванням секційного вимикача
    return failureRateTwoLinesSimultaneous + 0.02 // λдвок. = 0.0237
}

// Функція для розрахунку втрат потужності
fun calculatePowerLoss(
    emergencyRate: Double,
    plannedRate: Double,
    emergencyPowerLoss: Double = 14900.0, // Аварійне недовідпущення, кВт-год
    plannedPowerLoss: Double = 132400.0  // Планове недовідпущення, кВт-год
): Double {
    return (emergencyRate * emergencyPowerLoss) + (plannedRate * plannedPowerLoss)
}



