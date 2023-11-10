package com.activity.calculator.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CalculatorScreen(modifier: Modifier = Modifier) {
    var firstNum by remember { mutableStateOf("") }
    var secondNum by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = firstNum,
            onValueChange = { firstNum = it },
            label = { Text("First Number") },
			keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        TextField(
            value = secondNum,
            onValueChange = { secondNum = it },
            label = { Text("Second Number") },
			keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        operation = operationDropDown()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formatResult(result),
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement= Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier.width(175.dp),
                    onClick = {
                        val num1 = firstNum.toDoubleOrNull() ?: 0.0
                        val num2 = secondNum.toDoubleOrNull() ?: 0.0
                        when (operation) {
                            "Addition" -> result = (num1 + num2).toString()
                            "Subtraction" -> result = (num1 - num2).toString()
                            "Multiplication" -> result = (num1 * num2).toString()
                            "Division" -> {
                                if (num2 != 0.0) {
                                    result = (num1 / num2).toString()
                                } else {
                                    result = "Error: Division by zero"
                                }
                            }
                            else -> result = "Invalid operation"
                        }
                   }) {
                    Text("Calculate")
                }
                Button(
                    modifier = Modifier.width(175.dp),
                    onClick = {
                        firstNum = ""
                        secondNum = ""
                        result = "0"
                        operation = ""
                    }) {
                    Text("Reset")
                }
            }
        }
    }
}


// Function Helpers of Calculator
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun operationDropDown(): String {
    val operations = arrayOf("Addition", "Subtraction", "Multiplication", "Division")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Operation") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                operations.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    return selectedText
}

fun formatResult(result: String): String {
    try {
        val formattedResult = result.toDouble().toString()

        // Use DecimalFormat to format the result with a specified pattern
        val decimalFormat = DecimalFormat("#.##########")
        return decimalFormat.format(formattedResult.toDouble())

    } catch (e: NumberFormatException) {
        return result
    }
}


@Preview
@Composable
fun SliderMinimalExample() {
    var sliderPosition by remember { mutableFloatStateOf(50f) }
    var randomValue by remember { mutableStateOf(0) }

    fun reset() {
        sliderPosition = 50f // default slider value
        randomValue = 0 // default value of the generated number
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween, // Place buttons at the bottom
        horizontalAlignment = Alignment.CenterHorizontally // Center-align generated number
    ) {
        Text(text = sliderPosition.toInt().toString())
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f
        )
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${randomValue}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 50.sp,
                textAlign = TextAlign.Center // Center-align the generated number

            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement= Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier.width(175.dp),
                    onClick = { randomValue = Random.nextInt(0, sliderPosition.toInt() + 1) }) {
                    Text("Generate")
                }
                Button(
                    modifier = Modifier.width(175.dp),
                    onClick = { reset() }) {
                    Text("Reset")
                }
            }
        }
    }
}
