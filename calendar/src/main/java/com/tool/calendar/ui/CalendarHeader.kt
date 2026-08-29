package com.tool.calendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tool.calendar.model.CalendarViewMode
import com.tool.calendar.state.CalendarState
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Calendar header containing Month and Year interactive selection chips and navigation arrows.
 */
@Composable
fun CalendarHeader(
    state: CalendarState,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault(),
    showTodayButton: Boolean = true
) {
    val displayedMonthName = state.displayedMonth.month.getDisplayName(TextStyle.FULL, locale)
    val displayedYear = state.displayedMonth.year.toString()

    val isCurrentMonthToday = YearMonth.now() == state.displayedMonth

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Month and Year selector chips
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            // Month Chip
            CalendarHeaderChip(
                text = displayedMonthName,
                isSelected = state.viewMode == CalendarViewMode.MONTH_PICKER,
                onClick = { state.toggleMonthPicker() },
                colors = colors,
                typography = typography,
                shapes = shapes
            )

            // Year Chip
            CalendarHeaderChip(
                text = displayedYear,
                isSelected = state.viewMode == CalendarViewMode.YEAR_PICKER,
                onClick = { state.toggleYearPicker() },
                colors = colors,
                typography = typography,
                shapes = shapes
            )
        }

        // Action & Navigation buttons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Jump to Today button
            if (showTodayButton) {
                AnimatedVisibility(
                    visible = !isCurrentMonthToday || state.viewMode != CalendarViewMode.DATE_PICKER,
                    enter = fadeIn(animationSpec = tween(200)),
                    exit = fadeOut(animationSpec = tween(150))
                ) {
                    IconButton(
                        onClick = { state.jumpToToday() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Today,
                            contentDescription = "Jump to today",
                            tint = colors.todayTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Previous button
            IconButton(
                onClick = {
                    when (state.viewMode) {
                        CalendarViewMode.DATE_PICKER -> state.previousMonth()
                        CalendarViewMode.MONTH_PICKER -> state.selectYear(state.displayedMonth.year - 1)
                        CalendarViewMode.YEAR_PICKER -> state.selectYear(state.displayedMonth.year - 12)
                    }
                },
                enabled = state.canGoPrevious,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    tint = if (state.canGoPrevious) colors.headerIconColor else colors.disabledDayTextColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Next button
            IconButton(
                onClick = {
                    when (state.viewMode) {
                        CalendarViewMode.DATE_PICKER -> state.nextMonth()
                        CalendarViewMode.MONTH_PICKER -> state.selectYear(state.displayedMonth.year + 1)
                        CalendarViewMode.YEAR_PICKER -> state.selectYear(state.displayedMonth.year + 12)
                    }
                },
                enabled = state.canGoNext,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = if (state.canGoNext) colors.headerIconColor else colors.disabledDayTextColor,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

/**
 * Clickable header chip with smooth color transition and animated chevron.
 */
@Composable
private fun CalendarHeaderChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) colors.headerChipSelectedBackgroundColor else colors.headerChipBackgroundColor,
        animationSpec = tween(durationMillis = 220),
        label = "chipBgColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) colors.headerChipSelectedTextColor else colors.headerChipTextColor,
        animationSpec = tween(durationMillis = 220),
        label = "chipTextColor"
    )

    val chevronRotation by animateFloatAsState(
        targetValue = if (isSelected) 180f else 0f,
        animationSpec = spring(stiffness = 500f),
        label = "chevronRotation"
    )

    Box(
        modifier = modifier
            .clip(shapes.headerChipShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = contentColor),
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = typography.headerChipStyle,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier
                    .size(18.dp)
                    .rotate(chevronRotation)
            )
        }
    }
}
