package com.fittrack.app.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fittrack.app.ui.components.AnimatedBackground
import com.fittrack.app.ui.dashboard.DashboardScreen
import com.fittrack.app.ui.theme.AccentViolet
import com.fittrack.app.ui.theme.GlassBorder
import com.fittrack.app.ui.theme.GlassHi
import com.fittrack.app.ui.theme.GlassLo
import com.fittrack.app.ui.theme.TextSecondary
import com.fittrack.app.ui.today.TodayScreen

private enum class Tab(val label: String, val icon: ImageVector) {
    TODAY("Today", Icons.Filled.Today),
    DASHBOARD("Dashboard", Icons.Filled.BarChart),
}

@Composable
fun MainScaffold() {
    var selected by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        AnimatedBackground()
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(1f)) {
                when (selected) {
                    0 -> TodayScreen()
                    else -> DashboardScreen()
                }
            }
            GlassBottomBar(selected) { selected = it }
        }
    }
}

@Composable
private fun GlassBottomBar(selected: Int, onSelect: (Int) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Brush.verticalGradient(listOf(GlassLo, GlassHi)))
            .navigationBarsPadding()
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Tab.entries.forEachIndexed { index, tab ->
                NavItem(
                    tab = tab,
                    isSelected = selected == index,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect(index) }
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    tab: Tab,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val tint by animateColorAsState(
        if (isSelected) Color.White else TextSecondary, tween(250), label = "tint"
    )
    val pillAlpha by animateFloatAsState(if (isSelected) 1f else 0f, tween(250), label = "pill")

    Box(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) AccentViolet.copy(alpha = 0.22f * pillAlpha) else Color.Transparent
            )
            .border(
                width = 1.dp,
                color = if (isSelected) GlassBorder else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(tab.icon, contentDescription = tab.label, tint = tint, modifier = Modifier.size(20.dp))
            if (isSelected) {
                Spacer(Modifier.width(8.dp))
                Text(
                    tab.label,
                    color = tint,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
