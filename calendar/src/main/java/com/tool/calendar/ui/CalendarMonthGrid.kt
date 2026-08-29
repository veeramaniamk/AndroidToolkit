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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.tool.calendar.state.CalendarState
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Grid view for selecting a month.
 */
@Composable
fun CalendarMonthGrid(
    state: CalendarState,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault()
) {
    val months = remember { Month.values().toList() }
    val currentMonthValue = state.displayedMonth.month
    val now = LocalDate.now()
    val isThisYear = state.displayedMonth.year == now.year

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 280.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(months, key = { it.name }) { month ->
            val isSelected = month == currentMonthValue
            val isCurrentCalendarMonth = isThisYear && month == now.month
            val displayName = month.getDisplayName(TextStyle.SHORT, locale)

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.04f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "monthScale"
            )

            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) {
                    colors.pickerItemSelectedBackgroundColor
                } else {
                    colors.pickerItemHoverBackgroundColor
                },
                animationSpec = tween(200),
                label = "monthBgColor"
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected) {
                    colors.pickerItemSelectedTextColor
                } else if (isCurrentCalendarMonth) {
                    colors.todayTextColor
                } else {
                    colors.pickerItemTextColor
                },
                animationSpec = tween(200),
                label = "monthTextColor"
            )

            Box(
                modifier = Modifier
                    .aspectRatio(1.8f)
                    .scale(scale)
                    .clip(shapes.pickerItemShape)
                    .background(backgroundColor)
                    .then(
                        if (isCurrentCalendarMonth && !isSelected) {
                            Modifier.border(
                                width = 1.dp,
                                color = colors.todayBorderColor.copy(alpha = 0.6f),
                                shape = shapes.pickerItemShape
                            )
                        } else {
                            Modifier
                        }
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true, color = colors.pickerItemSelectedBackgroundColor),
                        role = Role.Button,
                        onClick = { state.selectMonth(month) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayName,
                    style = typography.pickerItemStyle.copy(
                        fontWeight = if (isSelected || isCurrentCalendarMonth) FontWeight.SemiBold else FontWeight.Medium
                    ),
                    color = textColor
                )
            }
        }
    }
}
