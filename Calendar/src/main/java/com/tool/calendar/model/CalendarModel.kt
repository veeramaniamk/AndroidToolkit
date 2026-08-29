package com.tool.calendar.model

import java.time.LocalDate

/**
 * Represents the current view mode of the calendar.
 */
enum class CalendarViewMode {
    DATE_PICKER,
    MONTH_PICKER,
    YEAR_PICKER
}

/**
 * Represents a single day cell in the calendar date grid.
 */
data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean,
    val isToday: Boolean,
    val isSelectable: Boolean
)
