package com.fittrack.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fittrack.app.ui.theme.GlassBorder
import com.fittrack.app.ui.theme.GlassHi
import com.fittrack.app.ui.theme.GlassLo

/** A frosted translucent surface with a soft top highlight and hairline border. */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    contentPadding: Dp = 18.dp,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.verticalGradient(listOf(GlassHi, GlassLo)))
            .border(1.dp, Brush.verticalGradient(listOf(GlassBorder, GlassLo)), shape)
            .padding(contentPadding)
    ) {
        content()
    }
}
