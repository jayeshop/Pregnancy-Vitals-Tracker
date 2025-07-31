package com.example.pregnancyvitalstracker.ui.components

import com.example.pregnancyvitalstracker.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pregnancyvitalstracker.data.Vital
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun VitalsList(
    vitals: List<Vital>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(vitals) { vital ->
            Card(
                modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEBB9FE)
                )
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            VitalInfoItem(
                                iconRes = R.drawable.heart_rate,
                                label = "${vital.heartRate} bpm"
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            VitalInfoItem(
                                iconRes = R.drawable.blood_pressure,
                                label = "${vital.systolic}/${vital.diastolic} mmHg"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            VitalInfoItem(
                                iconRes = R.drawable.scale,
                                label = "${vital.weight.toInt()} kg"
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            VitalInfoItem(
                                iconRes = R.drawable.newborn,
                                label = "${vital.kicks} kicks"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF9C4DB9))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        val formattedTime = SimpleDateFormat(
                            "EEE, dd MMM yyyy hh:mm a", Locale.ENGLISH
                        ).format(Date(vital.timestamp)).replace("AM", "am").replace("PM", "pm")

                        Text(
                            text = formattedTime,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun VitalInfoItem(iconRes: Int, label: String) {
    val textColor = Color(0xFF3F0A71)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
        )
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}