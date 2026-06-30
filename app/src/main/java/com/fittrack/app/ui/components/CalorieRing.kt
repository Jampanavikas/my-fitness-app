package com.fittrack.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fittrack.app.ui.theme.AccentAmber
import com.fittrack.app.ui.theme.AccentPink
import com.fittrack.app.ui.theme.AccentTeal
import com.fittrack.app.ui.theme.AccentViolet
import com.fittrack.app.ui.theme.TextSecondary

/** Animated calorie progress ring with a gradient sweep and centred totals. */
@Composable
fun CalorieRing(
    consumed: Int,
    target: Int,
    modifier: Modifier = Modifier,
    diameter: Dp = 168.dp,
) {
    val fraction = if (target <= 0) 0f else (consumed.toFloat() / target).coerceIn(0f, 1f)
    val animated by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(900),
        label = "ring"
    )
    val sweepColors = listOf(AccentTeal, AccentViolet, AccentPink, AccentAmber)

    Box(modifier = modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(diameter)) {
            val stroke = 18.dp.toPx()
            val inset = stroke / 2
            val arcSize = Size(size.width - stroke, size.height - stroke)
            val topLeft = Offset(inset, inset)

            // track
            drawArc(
                color = androidx.compose.ui.graphics.Color(0x22FFFFFF),
                startAngle = 0f, sweepAngle = 360f, useCenter = false,
                topLeft = topLeft, size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            // progress
            drawArc(
                brush = Brush.sweepGradient(sweepColors),
                startAngle = -90f,
                sweepAngle = 360f * animated,
                useCenter = false,
                topLeft = topLeft, size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Box(contentAlignment = Alignment.Center) {
            androidx.compose.foundation.layout.Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$consumed",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "of $target kcal",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
