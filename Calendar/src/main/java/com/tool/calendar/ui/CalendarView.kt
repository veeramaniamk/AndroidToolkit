package com.tool.calendar.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tool.calendar.model.CalendarViewMode
import com.tool.calendar.state.CalendarState
import com.tool.calendar.state.rememberCalendarState
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography
import com.tool.calendar.theme.LocalCalendarColors
import com.tool.calendar.theme.LocalCalendarShapes
import com.tool.calendar.theme.LocalCalendarTypography
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Reusable, customizable, and responsive Calendar composable.
 *
 * Supports Light & Dark theme, custom colors, custom typography (font family),
 * responsive sizes (small phones, tablets), month/year grid selection, and smooth animations.
 */
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    state: CalendarState = rememberCalendarState(),
    colors: CalendarColors = CalendarDefaults.colors(),
    typography: CalendarTypography = CalendarDefaults.typography(),
    shapes: CalendarShapes = CalendarDefaults.shapes(),
    onDateSelected: ((LocalDate) -> Unit)? = null,
    showTodayButton: Boolean = true,
    locale: Locale = Locale.getDefault()
) {
    CompositionLocalProvider(
        LocalCalendarColors provides colors,
        LocalCalendarTypography provides typography,
        LocalCalendarShapes provides shapes
    ) {
        Surface(
            modifier = modifier
                .widthIn(min = 280.dp, max = 480.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shapes.containerShape),
            color = colors.containerColor,
            shape = shapes.containerShape,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                // Header (Month & Year chips + Prev/Next buttons)
                CalendarHeader(
                    state = state,
                    colors = colors,
                    typography = typography,
                    shapes = shapes,
                    locale = locale,
                    showTodayButton = showTodayButton
                )

                HorizontalDivider(
                    color = colors.dividerColor,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
                )

                // Smooth view mode transition (Date Picker <-> Month Grid <-> Year Grid)
                AnimatedContent(
                    targetState = state.viewMode,
                    transitionSpec = {
                        val enterTransition = fadeIn(animationSpec = tween(220)) +
                                scaleIn(initialScale = 0.94f, animationSpec = tween(220, easing = FastOutSlowInEasing))
                        val exitTransition = fadeOut(animationSpec = tween(180)) +
                                scaleOut(targetScale = 0.94f, animationSpec = tween(180, easing = FastOutSlowInEasing))
                        enterTransition togetherWith exitTransition
                    },
                    label = "calendarViewModeTransition"
                ) { mode ->
                    when (mode) {
                        CalendarViewMode.DATE_PICKER -> {
                            CalendarDateView(
                                state = state,
                                colors = colors,
                                typography = typography,
                                shapes = shapes,
                                onDateSelected = onDateSelected,
                                locale = locale
                            )
                        }

                        CalendarViewMode.MONTH_PICKER -> {
                            CalendarMonthGrid(
                                state = state,
                                colors = colors,
                                typography = typography,
                                shapes = shapes,
                                locale = locale
                            )
                        }

                        CalendarViewMode.YEAR_PICKER -> {
                            CalendarYearGrid(
                                state = state,
                                colors = colors,
                                typography = typography,
                                shapes = shapes
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * The standard month calendar date view showing weekday headers and days grid.
 */
@Composable
private fun CalendarDateView(
    state: CalendarState,
    colors: CalendarColors,
    typography: CalendarTypography,
    shapes: CalendarShapes,
    onDateSelected: ((LocalDate) -> Unit)?,
    locale: Locale
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Weekday Row
        val weekdays = remember(state.firstDayOfWeek) { state.getWeekdays() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekdays.forEach { dayOfWeek ->
                val dayName = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                Text(
                    text = dayName.take(3),
                    style = typography.weekdayStyle,
                    color = colors.weekdayTextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Month Days Grid with smooth month sliding animation
        AnimatedContent(
            targetState = state.displayedMonth,
            transitionSpec = {
                if (targetState.isAfter(initialState)) {
                    (slideInHorizontally(
                        animationSpec = tween(220),
                        initialOffsetX = { it / 3 }
                    ) + fadeIn(animationSpec = tween(220))) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(200),
                                targetOffsetX = { -it / 3 }
                            ) + fadeOut(animationSpec = tween(200)))
                } else {
                    (slideInHorizontally(
                        animationSpec = tween(220),
                        initialOffsetX = { -it / 3 }
                    ) + fadeIn(animationSpec = tween(220))) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(200),
                                targetOffsetX = { it / 3 }
                            ) + fadeOut(animationSpec = tween(200)))
                }
            },
            label = "monthPagingTransition"
        ) { _ ->
            val days = state.getDaysForDisplayedMonth()

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                userScrollEnabled = false
            ) {
                items(days, key = { it.date.toString() }) { day ->
                    CalendarDayCell(
                        day = day,
                        colors = colors,
                        typography = typography,
                        shapes = shapes,
                        onDateClick = { selectedDay ->
                            state.selectDate(selectedDay.date)
                            onDateSelected?.invoke(selectedDay.date)
                        }
                    )
                }
            }
        }
    }
}
