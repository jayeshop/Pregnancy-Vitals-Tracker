package com.example.pregnancyvitalstracker.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pregnancyvitalstracker.ui.components.AddVitalDialog
import com.example.pregnancyvitalstracker.ui.components.VitalsList
import com.example.pregnancyvitalstracker.ui.theme.PregnancyVitalsTrackerTheme
import com.example.pregnancyvitalstracker.viewmodel.VitalViewModel
import com.example.pregnancyvitalstracker.worker.ReminderWorker
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        workManager = WorkManager.getInstance(applicationContext)
        scheduleVitalsReminder()
        createNotificationChannel()
        requestNotificationPermission()

        setContent {
            PregnancyVitalsTrackerTheme {
                val viewModel: VitalViewModel = viewModel()
                val vitals by viewModel.vitals.collectAsState()

                var showDialog by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Track My Pregnancy",
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFC8ADFC),
                                titleContentColor = Color(0xFF5F1C9C)
                            ),
                            modifier = Modifier.shadow(elevation = 4.dp)
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { showDialog = true },
                            shape = CircleShape,
                            containerColor = Color(0xFF9C4DB9),
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Vitals",
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                ) { innerPadding: PaddingValues ->
                    VitalsList(
                        vitals = vitals,
                        modifier = Modifier.padding(innerPadding)
                    )

                    if (showDialog) {
                        AddVitalDialog(
                            onDismiss = { showDialog = false },
                            onSubmit = { vital ->
                                viewModel.addVital(vital)
                                showDialog = false
                            }
                        )
                    }
                }
            }
        }
    }

    private fun scheduleVitalsReminder() {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(
            5, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "VitalsReminder",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "vitals_channel",
                "Vitals Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for vitals reminder notifications"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
    }
}
