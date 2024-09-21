package com.example.mobilecalculator1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Calculator1Activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator1)

        // Отримуємо поля введення для кожного компонента
        val carbonInput = findViewById<TextInputEditText>(R.id.inputField1)
        val hydrogenInput = findViewById<TextInputEditText>(R.id.inputField2)
        val sulfurInput = findViewById<TextInputEditText>(R.id.inputField3)
        val nitrogenInput = findViewById<TextInputEditText>(R.id.inputField4)
        val oxygenInput = findViewById<TextInputEditText>(R.id.inputField5)
        val moistureInput = findViewById<TextInputEditText>(R.id.inputField6)
        val ashInput = findViewById<TextInputEditText>(R.id.inputField7)

        val calculateButton = findViewById<Button>(R.id.calculate_button)

        // Текстові поля для відображення результатів
        val initialData = findViewById<TextView>(R.id.result_text_view)
        val coefficientWtoD = findViewById<TextView>(R.id.result1_text_view)
        val coefficientWtoC = findViewById<TextView>(R.id.result2_text_view)
        val compositionDryMass = findViewById<TextView>(R.id.result3_text_view)
        val compositionCombustibleMass = findViewById<TextView>(R.id.result4_text_view)
        val heatWorkingMass = findViewById<TextView>(R.id.result5_text_view)
        val heatDryMass = findViewById<TextView>(R.id.result6_text_view)
        val heatCombustibleMass = findViewById<TextView>(R.id.result7_text_view)

        // Приховування всіх текстових полів результатів до натискання кнопки
        toggleViewsVisibility(
            listOf(initialData, coefficientWtoD, coefficientWtoC,
                compositionDryMass, compositionCombustibleMass,
                heatWorkingMass, heatDryMass, heatCombustibleMass),
            View.GONE
        )

        calculateButton.setOnClickListener {
            // Отримання даних з полів введення
            val carbon = carbonInput.text.toString().toDoubleOrNull() ?: 0.0
            val hydrogen = hydrogenInput.text.toString().toDoubleOrNull() ?: 0.0
            val sulfur = sulfurInput.text.toString().toDoubleOrNull() ?: 0.0
            val nitrogen = nitrogenInput.text.toString().toDoubleOrNull() ?: 0.0
            val oxygen = oxygenInput.text.toString().toDoubleOrNull() ?: 0.0
            val moisture = moistureInput.text.toString().toDoubleOrNull() ?: 0.0
            val ash = ashInput.text.toString().toDoubleOrNull() ?: 0.0

            // Розрахунок коефіцієнтів для сухої та горючої маси
            val coefficientWtoDResult = formatValue(100/(100 - moisture))
            val coefficientWtoCResult = formatValue(100/(100 - moisture - ash))

            // Розрахунок маси сухої та горючої маси для кожного компонента
            val carbonDryMass = calculateMass(carbon, coefficientWtoDResult)
            val hydrogenDryMass = calculateMass(hydrogen, coefficientWtoDResult)
            val sulfurDryMass = calculateMass(sulfur, coefficientWtoDResult)
            val nitrogenDryMass = calculateMass(nitrogen, coefficientWtoDResult)
            val oxygenDryMass = calculateMass(oxygen, coefficientWtoDResult)
            val ashDryMass = calculateMass(ash, coefficientWtoDResult)

            val carbonCombustibleMass = calculateMass(carbon, coefficientWtoCResult)
            val hydrogenCombustibleMass = calculateMass(hydrogen, coefficientWtoCResult)
            val sulfurCombustibleMass = calculateMass(sulfur, coefficientWtoCResult)
            val nitrogenCombustibleMass = calculateMass(nitrogen, coefficientWtoCResult)
            val oxygenCombustibleMass = calculateMass(oxygen, coefficientWtoCResult)

            // Розрахунок нижчої теплоти згоряння для робочої, сухої та горючої маси
            val heatWorkingMassResult = formatValue((339*carbon + 1030*hydrogen- 108.8*(oxygen - sulfur) - 25*moisture)/1000)
            val heatDryMassResult = calculateHeatMass(heatWorkingMassResult, moisture)
            val heatCombustibleMassResult = calculateHeatMass(heatWorkingMassResult, moisture, ash)

            // Виведення результатів в текстові поля
            initialData.text = "Для палива з компонентним складом: " +
                    "C=$carbon%; " +
                    "H=$hydrogen%; " +
                    "S=$sulfur%; " +
                    "N=$nitrogen%; " +
                    "O=$oxygen%; " +
                    "W=$moisture%; " +
                    "A=$ash."
            coefficientWtoD.text = "Коефіцієнт переходу від робочої до сухої маси становить: $coefficientWtoDResult."
            coefficientWtoC.text = "Коефіцієнт переходу від робочої до горючої маси становить: $coefficientWtoCResult."
            compositionDryMass.text = "Склад сухої маси палива становитиме: " +
                    "C=$carbonDryMass%; " +
                    "H=$hydrogenDryMass%; " +
                    "S=$sulfurDryMass%; " +
                    "N=$nitrogenDryMass%; " +
                    "O=$oxygenDryMass%; " +
                    "A=$ashDryMass."
            compositionCombustibleMass.text = "Склад горючої маси палива становитиме: " +
                    "C=$carbonCombustibleMass%; " +
                    "H=$hydrogenCombustibleMass%; " +
                    "S=$sulfurCombustibleMass%; " +
                    "N=$nitrogenCombustibleMass%; " +
                    "O=$oxygenCombustibleMass%."
            heatWorkingMass.text = "Нижча теплота згоряння для робочої маси за заданим складом компонентів палива становить: $heatWorkingMassResult МДж/кг."
            heatDryMass.text = "Нижча теплота згоряння для сухої маси за заданим складом компонентів палива становить: $heatDryMassResult МДж/кг."
            heatCombustibleMass.text = "Нижча теплота згоряння для горючої маси за заданим складом компонентів палива становить: $heatCombustibleMassResult МДж/кг."

            // Відображення результатів після обчислень
            toggleViewsVisibility(
                listOf(initialData, coefficientWtoD, coefficientWtoC,
                    compositionDryMass, compositionCombustibleMass,
                    heatWorkingMass, heatDryMass, heatCombustibleMass),
                View.VISIBLE
            )
        }
    }

    // Функція для зміни видимості групи Views
    private fun toggleViewsVisibility(views: List<View>, visibility: Int) {
        views.forEach { it.visibility = visibility }
    }

    // Функція форматування значень до 2 знаків після коми
    @SuppressLint("DefaultLocale")
    private fun formatValue(value: Double): Double {
        return String.format("%.2f", value).toDouble()
    }

    // Функція розрахунку маси компонента з урахуванням коефіцієнта
    private fun calculateMass(component: Double, coefficient: Double): Double {
        return formatValue(component * coefficient)
    }

    // Розрахунок теплоти згоряння з урахуванням вмісту вологи та золи (якщо є)
    private fun calculateHeatMass(baseHeat: Double, moisture: Double, ash: Double? = null): Double {
        return if (ash != null) {
            formatValue((baseHeat + 0.025 * moisture) * 100 / (100 - moisture - ash))
        } else {
            formatValue((baseHeat + 0.025 * moisture) * 100 / (100 - moisture))
        }
    }
}