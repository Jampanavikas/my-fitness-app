package com.fittrack.app.ui.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fittrack.app.ui.components.GlassCard
import com.fittrack.app.ui.theme.AccentAmber
import com.fittrack.app.ui.theme.AccentGreen
import com.fittrack.app.ui.theme.AccentPink
import com.fittrack.app.ui.theme.AccentTeal
import com.fittrack.app.ui.theme.AccentViolet
import com.fittrack.app.ui.theme.TextPrimary
import com.fittrack.app.ui.theme.TextSecondary

@Composable
fun DashboardScreen(vm: DashboardViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column(Modifier.padding(vertical = 4.dp)) {
            Text("This week", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            Text(
                "Dashboard",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(state.rangeLabel, color = TextSecondary, style = MaterialTheme.typography.labelLarge)
        }

        WeeklyBars(state)

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            StatCard(
                Modifier.weight(1f), Icons.AutoMirrored.Filled.TrendingUp, AccentTeal,
                "${state.avgCompletion}%", "Avg completion"
            )
            StatCard(
                Modifier.weight(1f), Icons.Filled.LocalFireDepartment, AccentAmber,
                "${state.streak}", if (state.streak == 1) "day streak" else "day streak"
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            StatCard(
                Modifier.weight(1f), Icons.Filled.FitnessCenter, AccentPink,
                "${state.workoutsDone}/${state.workoutsTotal}", "Workouts"
            )
            StatCard(
                Modifier.weight(1f), Icons.Filled.CheckCircle, AccentGreen,
                "${state.tasksDone}/${state.tasksTotal}", "Tasks done"
            )
        }

        val remindersOn by vm.remindersEnabled.collectAsStateWithLifecycle()
        RemindersCard(remindersOn) { vm.setReminders(it) }
    }
}

@Composable
private fun RemindersCard(enabled: Boolean, onToggle: (Boolean) -> Unit) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AccentViolet.copy(alpha = 0.18f))
                    .border(1.dp, AccentViolet.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Notifications, null, tint = AccentViolet, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text("Daily reminders", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Text(
                    "Walk, learning, meals & workout times",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary
                )
            }
            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = AccentViolet,
                    uncheckedTrackColor = Color(0x33FFFFFF),
                )
            )
        }
    }
}

@Composable
private fun WeeklyBars(state: DashboardUiState) {
    GlassCard {
        Column {
            Text(
                "Daily completion",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                state.days.forEach { day ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val pct = (day.fraction * 100).toInt()
                        Text(
                            if (day.isFuture) "" else "$pct%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (day.isToday) AccentPink else TextSecondary,
                            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal
                        )
                        Spacer(Modifier.height(4.dp))
                        Bar(day.fraction, day.isToday)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            day.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (day.isToday) AccentPink else TextSecondary,
                            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Medium
                        )
                        Text(
                            "${day.dayOfMonth}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Bar(fraction: Float, isToday: Boolean) {
    val animated by animateFloatAsState(fraction.coerceIn(0f, 1f), tween(700), label = "bar")
    Box(
        modifier = Modifier
            .width(22.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(Color(0x1AFFFFFF)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(animated.coerceAtLeast(0.02f))
                .clip(RoundedCornerShape(11.dp))
                .background(
                    Brush.verticalGradient(
                        if (isToday) listOf(AccentPink, AccentViolet)
                        else listOf(AccentTeal, AccentViolet)
                    )
                )
                .then(
                    if (isToday) Modifier.border(1.dp, AccentPink, RoundedCornerShape(11.dp))
                    else Modifier
                )
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: ImageVector,
    accent: Color,
    value: String,
    label: String,
) {
    GlassCard(modifier = modifier) {
        Column {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accent.copy(alpha = 0.18f))
                    .border(1.dp, accent.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = accent, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(label, style = MaterialTheme.typography.labelLarge, color = TextSecondary)
        }
    }
}
