package com.fittrack.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.fittrack.app.ui.theme.AccentPink
import com.fittrack.app.ui.theme.AccentTeal
import com.fittrack.app.ui.theme.AccentViolet
import com.fittrack.app.ui.theme.BgDeep
import com.fittrack.app.ui.theme.BgMid
import com.fittrack.app.ui.theme.BgViolet

/**
 * A slow, lava-lamp style gradient backdrop. Three coloured blobs drift around
 * behind the translucent glass cards to give the frosted-glass depth.
 */
@Composable
fun AnimatedBackground(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "bg")

    val t1 by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(12000), RepeatMode.Reverse), label = "t1"
    )
    val t2 by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(9000), RepeatMode.Reverse), label = "t2"
    )
    val t3 by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(15000), RepeatMode.Reverse), label = "t3"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgDeep, BgMid, BgViolet))
            )
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            blob(AccentViolet, Offset(w * (0.15f + 0.3f * t1), h * (0.12f + 0.1f * t1)), w * 0.75f)
            blob(AccentTeal, Offset(w * (0.85f - 0.25f * t2), h * (0.30f + 0.15f * t2)), w * 0.65f)
            blob(AccentPink, Offset(w * (0.5f + 0.2f * t3), h * (0.78f - 0.12f * t3)), w * 0.7f)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.blob(
    color: Color,
    center: Offset,
    radius: Float,
) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(color.copy(alpha = 0.38f), Color.Transparent),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )
}
