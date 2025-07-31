package com.example.pregnancyvitalstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pregnancyvitalstracker.data.Vital


@Composable
fun AddVitalDialog(onDismiss: () -> Unit, onSubmit: (Vital) -> Unit) {
    var systolic by remember { mutableStateOf("") }
    var diastolic by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var kicks by remember { mutableStateOf("") }

    // Error state variables
    var systolicError by remember { mutableStateOf<String?>(null) }
    var diastolicError by remember { mutableStateOf<String?>(null) }
    var heartRateError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var kicksError by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        var valid = true

        systolicError = if (systolic.isBlank() || systolic.toIntOrNull() == null || systolic.toInt() <= 0) {
            valid = false
            "Enter valid systolic BP"
        } else null

        diastolicError = if (diastolic.isBlank() || diastolic.toIntOrNull() == null || diastolic.toInt() <= 0) {
            valid = false
            "Enter valid diastolic BP"
        } else null

        heartRateError = if (heartRate.isBlank() || heartRate.toIntOrNull() == null || heartRate.toInt() <= 0) {
            valid = false
            "Enter valid heart rate"
        } else null

        weightError = if (weight.isBlank() || weight.toFloatOrNull() == null || weight.toFloat() <= 0f) {
            valid = false
            "Enter valid weight"
        } else null

        kicksError = if (kicks.isBlank() || kicks.toIntOrNull() == null || kicks.toInt() < 0) {
            valid = false
            "Enter valid kicks count"
        } else null

        return valid
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Vitals", color = Color(0xFF3F0A71), fontWeight = FontWeight.SemiBold) },
        shape = RoundedCornerShape(8.dp),
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = systolic,
                        onValueChange = {
                            systolic = it
                            systolicError = null
                        },
                        label = { Text("Sys BP") },
                        isError = systolicError != null,
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = diastolic,
                        onValueChange = {
                            diastolic = it
                            diastolicError = null
                        },
                        label = { Text("Dia BP") },
                        isError = diastolicError != null,
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                if (systolicError != null || diastolicError != null) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = systolicError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = diastolicError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = heartRate,
                    onValueChange = {
                        heartRate = it
                        heartRateError = null
                    },
                    label = { Text("Heart Rate") },
                    isError = heartRateError != null,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (heartRateError != null) {
                    Text(
                        text = heartRateError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        weightError = null
                    },
                    label = { Text("Weight (in kg)") },
                    isError = weightError != null,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (weightError != null) {
                    Text(
                        text = weightError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = kicks,
                    onValueChange = {
                        kicks = it
                        kicksError = null
                    },
                    label = { Text("Baby Kicks") },
                    isError = kicksError != null,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (kicksError != null) {
                    Text(
                        text = kicksError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        },
        confirmButton = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (validate()) {
                            onSubmit(
                                Vital(
                                    systolic = systolic.toInt(),
                                    diastolic = diastolic.toInt(),
                                    heartRate = heartRate.toInt(),
                                    weight = weight.toFloat(),
                                    kicks = kicks.toInt()
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C4DB9),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Submit")
                }
            }
        },
        dismissButton = {}
    )
}