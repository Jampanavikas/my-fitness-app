package com.fittrack.app.ui.today

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import android.net.Uri
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.TaskCategory
import com.fittrack.app.ui.components.CalorieRing
import com.fittrack.app.ui.components.ExerciseGif
import com.fittrack.app.ui.components.GlassCard
import com.fittrack.app.ui.theme.AccentAmber
import com.fittrack.app.ui.theme.AccentBlue
import com.fittrack.app.ui.theme.AccentGreen
import com.fittrack.app.ui.theme.AccentPink
import com.fittrack.app.ui.theme.AccentTeal
import com.fittrack.app.ui.theme.AccentViolet
import com.fittrack.app.ui.theme.TextPrimary
import com.fittrack.app.ui.theme.TextSecondary

@Composable
fun TodayScreen(vm: TodayViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Header(state) }
        item { SummaryCard(state) }
        item { WaterCard(state, onAdd = { vm.addWater(1) }, onRemove = { vm.addWater(-1) }) }
        item {
            Text(
                "Today's plan",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                modifier = Modifier.padding(start = 4.dp, top = 6.dp)
            )
        }
        items(state.tasks, key = { it.id }) { task ->
            TaskRow(task = task, onToggle = { vm.toggle(task) })
        }
    }
}

@Composable
private fun Header(state: TodayUiState) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(state.dateLabel, color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            Text(
                state.greeting,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        GlassCard(contentPadding = 12.dp, shape = RoundedCornerShape(18.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("GOAL", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                Text(
                    "80 → 72 kg",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AccentTeal
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(state: TodayUiState) {
    GlassCard {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CalorieRing(
                    consumed = state.consumedKcal,
                    target = state.targetKcal,
                    diameter = 150.dp
                )
                Spacer(Modifier.width(18.dp))
                Column(Modifier.weight(1f)) {
                    StatLine(Icons.Filled.LocalFireDepartment, AccentAmber,
                        "${state.targetKcal - state.consumedKcal} kcal left")
                    Spacer(Modifier.height(14.dp))
                    Text("Protein", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = {
                            if (state.targetProtein == 0) 0f
                            else (state.consumedProtein.toFloat() / state.targetProtein).coerceIn(0f, 1f)
                        },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = AccentGreen,
                        trackColor = Color(0x22FFFFFF),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${state.consumedProtein} / ${state.targetProtein} g",
                        color = TextPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            val dayFraction =
                if (state.totalCount == 0) 0f else state.doneCount.toFloat() / state.totalCount
            val animatedDay by animateFloatAsState(dayFraction, tween(700), label = "day")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${state.doneCount}/${state.totalCount} done",
                    color = TextPrimary,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(Modifier.width(12.dp))
                LinearProgressIndicator(
                    progress = { animatedDay },
                    modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape),
                    color = AccentViolet,
                    trackColor = Color(0x22FFFFFF),
                )
            }
        }
    }
}

@Composable
private fun StatLine(icon: ImageVector, tint: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun WaterCard(state: TodayUiState, onAdd: () -> Unit, onRemove: () -> Unit) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBadge(Icons.Filled.WaterDrop, AccentBlue)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text("Water", color = TextPrimary, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${state.water} / ${state.waterTarget} glasses",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            RoundIconButton(Icons.Filled.Remove, onRemove)
            Spacer(Modifier.width(10.dp))
            RoundIconButton(Icons.Filled.Add, onAdd)
        }
    }
}

@Composable
private fun RoundIconButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0x1FFFFFFF))
            .border(1.dp, Color(0x33FFFFFF), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun TaskRow(task: DayTask, onToggle: () -> Unit) {
    val category = TaskCategory.from(task.category)
    val (icon, accent) = iconFor(category)
    val checkColor by animateColorAsState(
        if (task.done) accent else Color(0x14FFFFFF), tween(250), label = "check"
    )
    val checkScale by animateFloatAsState(if (task.done) 1f else 0.85f, tween(250), label = "scale")
    val contentAlpha by animateFloatAsState(if (task.done) 0.55f else 1f, tween(250), label = "alpha")
    var expanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(if (expanded) 180f else 0f, tween(250), label = "arrow")

    GlassCard(modifier = Modifier.fillMaxWidth(), contentPadding = 14.dp) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconBadge(icon, accent, alpha = contentAlpha)
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            task.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary.copy(alpha = contentAlpha),
                            textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                        )
                        if (task.time.isNotEmpty()) {
                            Spacer(Modifier.width(8.dp))
                            Text(
                                task.time,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }
                    if (task.subtitle.isNotEmpty()) {
                        Text(
                            task.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary.copy(alpha = contentAlpha)
                        )
                    }
                    if (task.kcal > 0) {
                        Text(
                            "${task.kcal} kcal • ${task.protein}g protein",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentAmber.copy(alpha = contentAlpha)
                        )
                    }
                }
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .scale(checkScale)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(checkColor)
                        .border(1.5.dp, if (task.done) accent else Color(0x33FFFFFF), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (task.done) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "done",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            if (task.howTo.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { expanded = !expanded }
                        .padding(vertical = 4.dp, horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Lightbulb, null, tint = accent, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        if (expanded) "Hide" else "How to do it",
                        style = MaterialTheme.typography.labelLarge,
                        color = accent,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Filled.ExpandMore, null, tint = accent,
                        modifier = Modifier.size(18.dp).rotate(arrowRotation)
                    )
                    if (category != TaskCategory.REST) {
                        val uri = LocalUriHandler.current
                        Spacer(Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    uri.openUri("https://www.youtube.com/results?search_query=" +
                                        Uri.encode(task.title + " proper form"))
                                }
                                .padding(vertical = 4.dp, horizontal = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.PlayCircle, null, tint = accent, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Watch demo", style = MaterialTheme.typography.labelLarge,
                                color = accent, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                AnimatedVisibility(visible = expanded) {
                    Column(Modifier.padding(top = 8.dp)) {
                        if (task.imageUrl.isNotEmpty()) {
                            ExerciseGif(task.imageUrl)
                            Spacer(Modifier.height(10.dp))
                        }
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x14FFFFFF))
                                .padding(12.dp)
                        ) {
                            Text(
                                task.howTo,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IconBadge(icon: ImageVector, accent: Color, alpha: Float = 1f) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(accent.copy(alpha = 0.18f * alpha))
            .border(1.dp, accent.copy(alpha = 0.35f * alpha), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = accent.copy(alpha = alpha), modifier = Modifier.size(24.dp))
    }
}

private fun iconFor(category: TaskCategory): Pair<ImageVector, Color> = when (category) {
    TaskCategory.WALK -> Icons.AutoMirrored.Filled.DirectionsWalk to AccentTeal
    TaskCategory.LEARN -> Icons.AutoMirrored.Filled.MenuBook to AccentBlue
    TaskCategory.MEAL -> Icons.Filled.Restaurant to AccentAmber
    TaskCategory.WARMUP -> Icons.Filled.Bolt to AccentAmber
    TaskCategory.WORKOUT -> Icons.Filled.FitnessCenter to AccentPink
    TaskCategory.SPORT -> Icons.Filled.SportsSoccer to AccentViolet
    TaskCategory.REST -> Icons.Filled.SelfImprovement to AccentGreen
    TaskCategory.STRETCH -> Icons.Filled.Accessibility to AccentTeal
}
