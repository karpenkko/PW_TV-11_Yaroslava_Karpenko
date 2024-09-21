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

class Calculator2Activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator2)

        // Отримуємо поля введення для кожного компонента
        val carbonInput = findViewById<TextInputEditText>(R.id.inputField1)
        val hydrogenInput = findViewById<TextInputEditText>(R.id.inputField2)
        val oxygenInput = findViewById<TextInputEditText>(R.id.inputField3)
        val sulfurInput = findViewById<TextInputEditText>(R.id.inputField4)
        val oilHeatInput = findViewById<TextInputEditText>(R.id.inputField5)
        val fuelMoistureInput = findViewById<TextInputEditText>(R.id.inputField6)
        val ashInput = findViewById<TextInputEditText>(R.id.inputField7)
        val vanadiumInput = findViewById<TextInputEditText>(R.id.inputField8)

        val calculateButton = findViewById<Button>(R.id.calculate_button)

        // Текстові поля для відображення результатів
        val initialData = findViewById<TextView>(R.id.result_text_view)
        val compositionFuelOilWM = findViewById<TextView>(R.id.result1_text_view)
        val lowerHeat = findViewById<TextView>(R.id.result2_text_view)

        // Приховування всіх текстових полів результатів до натискання кнопки
        toggleViewsVisibility(
            listOf(initialData, compositionFuelOilWM, lowerHeat),
            View.GONE
        )

        calculateButton.setOnClickListener {
            // Отримання даних з полів введення
            val carbon = carbonInput.text.toString().toDoubleOrNull() ?: 0.0
            val hydrogen = hydrogenInput.text.toString().toDoubleOrNull() ?: 0.0
            val oxygen = oxygenInput.text.toString().toDoubleOrNull() ?: 0.0
            val sulfur = sulfurInput.text.toString().toDoubleOrNull() ?: 0.0
            val oilHeat = oilHeatInput.text.toString().toDoubleOrNull() ?: 0.0
            val fuelMoisture = fuelMoistureInput.text.toString().toDoubleOrNull() ?: 0.0
            val ash = ashInput.text.toString().toDoubleOrNull() ?: 0.0
            val vanadium = vanadiumInput.text.toString().toDoubleOrNull() ?: 0.0

            // Обчислення коефіцієнтів для подальших розрахунків
            val factor1 = (100 - fuelMoisture - ash) / 100
            val factor2 = (100 - fuelMoisture / 10 - ash / 10) / 100
            val factor3 = (100 - fuelMoisture) / 100

            // Обчислення маси кожного компонента на робочу масу
            val carbonWorkingMass = formatValue(carbon * factor1)
            val hydrogenWorkingMass = formatValue(hydrogen * factor1)
            val oxygenWorkingMass = formatValue(oxygen * factor2)
            val sulfurWorkingMass = formatValue(sulfur * factor1)
            val ashWorkingMass = formatValue(ash * factor3)
            val vanadiumWorkingMass = formatValue(vanadium * factor3)

            // Обчислення нижчої теплоти згоряння
            val lowerHeatResult = formatValue(oilHeat * factor1 - 0.025 * fuelMoisture)

            // Виведення результатів в текстові поля
            initialData.text =
                "Для складу горючої маси мазуту, що задано наступними параметрами: " +
                    "H=$hydrogen%; " +
                    "C=$carbon%; " +
                    "O=$oxygen%; " +
                    "S=$sulfur%; " +
                    "V=$vanadium мг/кг; " +
                    "W=$fuelMoisture%; " +
                    "A=$ash " +
                    "та нижчою теплотою згоряння горючої маси мазуту Q=$oilHeat МДж/кг:"
            compositionFuelOilWM.text = "Склад робочої маси мазуту становитиме: " +
                    "H=$hydrogenWorkingMass%; " +
                    "C=$carbonWorkingMass%; " +
                    "S=$sulfurWorkingMass%; " +
                    "O=$oxygenWorkingMass%; " +
                    "V=$vanadiumWorkingMass мг/кг; " +
                    "A=$ashWorkingMass%"
            lowerHeat.text = "Нижча теплота згоряння мазуту на робочу масу для робочої маси за заданим складом" +
                        "компонентів палива становить:: $lowerHeatResult МДж/кг"

            // Відображення результатів після обчислень
            toggleViewsVisibility(
                listOf(initialData, compositionFuelOilWM, lowerHeat),
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
}