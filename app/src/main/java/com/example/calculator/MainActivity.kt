/*
 * RESOURCES USE ACKNOWLEDGEMENT
 *
 * For this assignment, since it is a really complicated question, I did research
 * online and found some really good examples. I tried to implemented them into
 * my assignment to have a better understanding about Kotlin and mobile app
 * development. However, since some features are too complicated for me to master
 * in a single week, for example, different themes in dark mode or not, and
 * advanced math equation, I picked some features and implemented them into my
 * mobile app. Here are some resources I had researched on:
 *
 * KotlinCalculator by callumhilldeveloper:
 * https://youtu.be/2hSHgungOKI?si=YEGTmCAdYhrzdTO4
 * https://github.com/codeWithCal/KotlinCalculator.git
 *
 * Android_CalculatorApp by bimalkaf:
 * https://youtu.be/X3KQdwVlo1Q?si=mVxKQLhbJEc5zAGk
 * https://github.com/bimalkaf/Android_CalculatorApp.git
 */


package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)

        // Set listeners for all the buttons
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot,
            R.id.buttonAddition, R.id.buttonSubtraction, R.id.buttonMultiplition,
            R.id.buttonDivide, R.id.buttonSquareRoot, R.id.buttonEqual
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { v -> onButtonClick(v) }
        }
    }

    private fun onButtonClick(v: View) {
        when (v.id) {
            R.id.button0 -> appendText("0")
            R.id.button1 -> appendText("1")
            R.id.button2 -> appendText("2")
            R.id.button3 -> appendText("3")
            R.id.button4 -> appendText("4")
            R.id.button5 -> appendText("5")
            R.id.button6 -> appendText("6")
            R.id.button7 -> appendText("7")
            R.id.button8 -> appendText("8")
            R.id.button9 -> appendText("9")
            R.id.buttonDot -> appendText(".")
            R.id.buttonAddition -> appendText("+")
            R.id.buttonSubtraction -> appendText("-")
            R.id.buttonMultiplition -> appendText("*")
            R.id.buttonDivide -> appendText("/")
            R.id.buttonSquareRoot -> calculateSquareRoot()
            R.id.buttonEqual -> calculateResult()
        }
    }

    private fun appendText(text: String) {
        editTextNumber.append(text)
    }

    private fun calculateSquareRoot() {
        val value = editTextNumber.text.toString().toDoubleOrNull() ?: return
        editTextNumber.setText(sqrt(value).toString())
    }

    private fun calculateResult() {
        try {
            val result = evaluateExpression(editTextNumber.text.toString())
            editTextNumber.setText(result.toString())
        } catch (e: Exception) {
            editTextNumber.setText("Error")
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Split the expression by space for simplicity
        val tokens = expression.split(" ")
        var result = 0.0
        var currentOperation: Char? = null

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> {
                    val number = token.toDouble()
                    result = when (currentOperation) {
                        '+' -> result + number
                        '-' -> result - number
                        '*' -> result * number
                        '/' -> {
                            if (number == 0.0) throw ArithmeticException("Cannot divide by zero")
                            result / number
                        }
                        else -> number // First number in the expression
                    }
                }
                token.length == 1 && "*/+-".contains(token) -> currentOperation = token[0]
                else -> throw IllegalArgumentException("Invalid token: $token")
            }
        }

        return result
    }
}
