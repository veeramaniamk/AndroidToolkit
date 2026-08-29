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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

/**
 * Grid view for selecting a year.
 */
@Composable
fun CalendarYearGrid(
    state: CalendarState,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    modifier: Modifier = Modifier,
    startYear: Int = 1920,
    endYear: Int = 2100
) {
    val minYear = state.minDate?.year ?: startYear
    val maxYear = state.maxDate?.year ?: endYear
    val years = remember(minYear, maxYear) { (minYear..maxYear).toList() }

    val currentYear = state.displayedMonth.year
    val nowYear = LocalDate.now().year

    val gridState = rememberLazyGridState()

    // Auto-scroll to the currently selected year when grid is displayed
    LaunchedEffect(currentYear) {
        val targetIndex = years.indexOf(currentYear)
        if (targetIndex >= 0) {
            val scrollIndex = (targetIndex - 3).coerceAtLeast(0)
            gridState.scrollToItem(scrollIndex)
        }
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 280.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(years, key = { it }) { year ->
            val isSelected = year == currentYear
            val isCurrentSystemYear = year == nowYear

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.04f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "yearScale"
            )

            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) {
                    colors.pickerItemSelectedBackgroundColor
                } else {
                    colors.pickerItemHoverBackgroundColor
                },
                animationSpec = tween(200),
                label = "yearBgColor"
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected) {
                    colors.pickerItemSelectedTextColor
                } else if (isCurrentSystemYear) {
                    colors.todayTextColor
                } else {
                    colors.pickerItemTextColor
                },
                animationSpec = tween(200),
                label = "yearTextColor"
            )

            Box(
                modifier = Modifier
                    .aspectRatio(1.8f)
                    .scale(scale)
                    .clip(shapes.pickerItemShape)
                    .background(backgroundColor)
                    .then(
                        if (isCurrentSystemYear && !isSelected) {
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
                        onClick = { state.selectYear(year) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = year.toString(),
                    style = typography.pickerItemStyle.copy(
                        fontWeight = if (isSelected || isCurrentSystemYear) FontWeight.SemiBold else FontWeight.Medium
                    ),
                    color = textColor
                )
            }
        }
    }
}
