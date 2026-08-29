package com.tool.calendar.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tool.calendar.model.CalendarDay
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography

/**
 * Individual date cell within the calendar grid.
 */
@Composable
fun CalendarDayCell(
    day: CalendarDay,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    onDateClick: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEnabled = day.isSelectable

    // Animated scale effect when selected
    val scale by animateFloatAsState(
        targetValue = if (day.isSelected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cellScale"
    )

    // Background color animation
    val targetBackgroundColor = when {
        day.isSelected -> colors.selectedDayBackgroundColor
        else -> Color.Transparent
    }
    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(durationMillis = 200),
        label = "cellBgColor"
    )

    // Text color animation
    val targetTextColor = when {
        day.isSelected -> colors.selectedDayTextColor
        !isEnabled -> colors.disabledDayTextColor
        !day.isCurrentMonth -> colors.adjacentMonthDayTextColor
        day.isToday -> colors.todayTextColor
        else -> colors.dayTextColor
    }
    val textColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = tween(durationMillis = 200),
        label = "cellTextColor"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.92f)
                .scale(scale)
                .clip(shapes.dayShape)
                .background(backgroundColor)
                .then(
                    if (day.isToday && !day.isSelected) {
                        Modifier.border(
                            width = 1.5.dp,
                            color = colors.todayBorderColor,
                            shape = shapes.dayShape
                        )
                    } else {
                        Modifier
                    }
                )
                .clickable(
                    enabled = isEnabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, color = colors.selectedDayBackgroundColor),
                    role = Role.Button,
                    onClick = { onDateClick(day) }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = typography.dayStyle.copy(
                    fontWeight = if (day.isSelected || day.isToday) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = textColor
            )
        }
    }
}
