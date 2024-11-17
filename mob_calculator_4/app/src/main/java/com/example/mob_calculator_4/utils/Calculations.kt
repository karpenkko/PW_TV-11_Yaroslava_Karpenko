package com.example.mob_calculator_4.utils

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Розрахунок економічного та термічного перерізу кабелю для двотрансформаторної підстанції
 * @param voltage Напруга в кіловольтах (кВ)
 * @return Пара значень: економічний переріз та термічний переріз кабелю
 */
fun calculateCableParameters(voltage: Double): Pair<Double, Double> {
    val shortCircuitCurrent = 2500.0
    val disconnectionTime = 2.5
    val loadPower = 1300.0
    val economicDensity = 1.4
    val thermalConstant = 92.0

    val inNormal = loadPower / (2 * sqrt(3.0) * voltage)
    val economicSection = inNormal / economicDensity
    val thermalSection = shortCircuitCurrent * sqrt(disconnectionTime) / thermalConstant

    return Pair(economicSection, thermalSection)
}

/**
 * Розрахунок струму короткого замикання
 * @param pKz Потужність короткого замикання (МВА)
 * @return Струм короткого замикання (А)
 */
fun calculateShortCircuitCurrent(pKz: Double): Double {
    val voltage = 10000.0
    val resistanceK1 = 0.55
    val resistanceK2 = 1.84
    val sqrt3 = sqrt(3.0)

    return pKz / (voltage * sqrt3 * (resistanceK1 + resistanceK2))
}

/**
 * Розрахунок струмів трифазного і двофазного КЗ в нормальному і мінімальному режимах
 * @param Rh Опір у нормальному режимі
 * @param Xh Реактивний опір у нормальному режимі
 * @param Rm Опір у мінімальному режимі
 * @param Xm Реактивний опір у мінімальному режимі
 * @return Текст з результатами розрахунків
 */
fun calculateShortCircuitCurrents(
    Rh: Double, Xh: Double, Rm: Double, Xm: Double
): String {
    val nominalVoltage = 115.0
    val baseVoltage = 11.0
    val sqrt3 = sqrt(3.0)
    val multiplier = 10.0.pow(3)

    val Xt = (11.1 * nominalVoltage.pow(2)) / (100 * 6.3)

    val Zsh = sqrt(Rh.pow(2) + (Xh + Xt).pow(2))
    val ZshMin = sqrt(Rm.pow(2) + (Xm + Xt).pow(2))

    val Ish3Normal = (nominalVoltage * multiplier) / (sqrt3 * Zsh)
    val Ish3Min = (nominalVoltage * multiplier) / (sqrt3 * ZshMin)

    val Ish2Normal = Ish3Normal * (sqrt3 / 2)
    val Ish2Min = Ish3Min * (sqrt3 / 2)

    val k = baseVoltage.pow(2) / nominalVoltage.pow(2)
    val ZshTrue = sqrt((Rh * k).pow(2) + ((Xh + Xt) * k).pow(2))
    val ZshMinTrue = sqrt((Rm * k).pow(2) + ((Xm + Xt) * k).pow(2))

    val DIsh3Normal = (baseVoltage * multiplier) / (sqrt3 * ZshTrue)
    val DIsh3Min = (baseVoltage * multiplier) / (sqrt3 * ZshMinTrue)

    val DIsh2Normal = DIsh3Normal * (sqrt3 / 2)
    val DIsh2Min = DIsh3Min * (sqrt3 / 2)

    return buildString {
        append("Результати розрахунків:\n")
        append("Струм трифазного КЗ (приведений до 110 кВ):\n")
        append("Нормальний режим: ${"%.2f".format(Ish3Normal)} А\n")
        append("Мінімальний режим: ${"%.2f".format(Ish3Min)} А\n\n")

        append("Струм двофазного КЗ (приведений до 110 кВ):\n")
        append("Нормальний режим: ${"%.2f".format(Ish2Normal)} А\n")
        append("Мінімальний режим: ${"%.2f".format(Ish2Min)} А\n\n")

        append("Дійсний струм трифазного КЗ (на шинах 10 кВ):\n")
        append("Нормальний режим: ${"%.2f".format(DIsh3Normal)} А\n")
        append("Мінімальний режим: ${"%.2f".format(DIsh3Min)} А\n\n")

        append("Дійсний струм двофазного КЗ (на шинах 10 кВ):\n")
        append("Нормальний режим: ${"%.2f".format(DIsh2Normal)} А\n")
        append("Мінімальний режим: ${"%.2f".format(DIsh2Min)} А\n\n")

        append("Аварійний режим на даній підстанції не передбачений.")
    }
}
