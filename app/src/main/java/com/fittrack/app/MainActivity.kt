package com.fittrack.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.fittrack.app.notify.Notifications
import com.fittrack.app.notify.ReminderScheduler
import com.fittrack.app.ui.MainScaffold
import com.fittrack.app.ui.theme.FitTrackTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { /* no-op */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        Notifications.ensureChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotifications.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        // Arm daily reminders if the user has them enabled (default on).
        lifecycleScope.launch {
            val app = application as FitTrackApp
            if (app.repository.remindersEnabledOnce()) {
                ReminderScheduler.scheduleAll(this@MainActivity)
            }
        }

        setContent {
            FitTrackTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
                    MainScaffold()
                }
            }
        }
    }
}
