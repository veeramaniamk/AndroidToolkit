package com.tool.calendar.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.tool.calendar.model.CalendarDay
import com.tool.calendar.model.CalendarViewMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * State holder for the Calendar component.
 */
@Stable
class CalendarState(
    initialSelectedDate: LocalDate? = null,
    initialDisplayedMonth: YearMonth = initialSelectedDate?.let { YearMonth.from(it) } ?: YearMonth.now(),
    initialViewMode: CalendarViewMode = CalendarViewMode.DATE_PICKER,
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,
    val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
) {
    var selectedDate by mutableStateOf(initialSelectedDate)
        private set

    var displayedMonth by mutableStateOf(initialDisplayedMonth)
        private set

    var viewMode by mutableStateOf(initialViewMode)
        private set

    val isMonthPickerOpen: Boolean
        get() = viewMode == CalendarViewMode.MONTH_PICKER

    val isYearPickerOpen: Boolean
        get() = viewMode == CalendarViewMode.YEAR_PICKER

    val canGoPrevious: Boolean
        get() {
            val min = minDate ?: return true
            return displayedMonth.isAfter(YearMonth.from(min))
        }

    val canGoNext: Boolean
        get() {
            val max = maxDate ?: return true
            return displayedMonth.isBefore(YearMonth.from(max))
        }

    fun selectDate(date: LocalDate) {
        if (isDateSelectable(date)) {
            selectedDate = date
            if (YearMonth.from(date) != displayedMonth) {
                displayedMonth = YearMonth.from(date)
            }
        }
    }

    fun clearSelection() {
        selectedDate = null
    }

    fun selectMonth(month: Month) {
        displayedMonth = YearMonth.of(displayedMonth.year, month)
        viewMode = CalendarViewMode.DATE_PICKER
    }

    fun selectYear(year: Int) {
        displayedMonth = YearMonth.of(year, displayedMonth.month)
        viewMode = CalendarViewMode.DATE_PICKER
    }

    fun previousMonth() {
        if (canGoPrevious) {
            displayedMonth = displayedMonth.minusMonths(1)
        }
    }

    fun nextMonth() {
        if (canGoNext) {
            displayedMonth = displayedMonth.plusMonths(1)
        }
    }

    fun toggleMonthPicker() {
        viewMode = if (viewMode == CalendarViewMode.MONTH_PICKER) {
            CalendarViewMode.DATE_PICKER
        } else {
            CalendarViewMode.MONTH_PICKER
        }
    }

    fun toggleYearPicker() {
        viewMode = if (viewMode == CalendarViewMode.YEAR_PICKER) {
            CalendarViewMode.DATE_PICKER
        } else {
            CalendarViewMode.YEAR_PICKER
        }
    }

    fun updateViewMode(mode: CalendarViewMode) {
        viewMode = mode
    }

    fun jumpToToday() {
        val today = LocalDate.now()
        displayedMonth = YearMonth.from(today)
        viewMode = CalendarViewMode.DATE_PICKER
    }

    fun isDateSelectable(date: LocalDate): Boolean {
        if (minDate != null && date.isBefore(minDate)) return false
        if (maxDate != null && date.isAfter(maxDate)) return false
        return true
    }

    fun getWeekdays(): List<DayOfWeek> {
        val days = mutableListOf<DayOfWeek>()
        var current = firstDayOfWeek
        repeat(7) {
            days.add(current)
            current = current.plus(1)
        }
        return days
    }

    fun getDaysForDisplayedMonth(): List<CalendarDay> {
        val today = LocalDate.now()
        val firstOfMonth = displayedMonth.atDay(1)
        val lastOfMonth = displayedMonth.atEndOfMonth()

        val daysList = mutableListOf<CalendarDay>()

        // Calculate leading days from previous month
        var leadingDaysCount = (firstOfMonth.dayOfWeek.value - firstDayOfWeek.value) % 7
        if (leadingDaysCount < 0) leadingDaysCount += 7

        if (leadingDaysCount > 0) {
            val prevMonthLastDay = firstOfMonth.minusDays(1)
            val startLeadingDay = prevMonthLastDay.minusDays((leadingDaysCount - 1).toLong())
            var cur = startLeadingDay
            while (!cur.isAfter(prevMonthLastDay)) {
                daysList.add(
                    CalendarDay(
                        date = cur,
                        isCurrentMonth = false,
                        isSelected = cur == selectedDate,
                        isToday = cur == today,
                        isSelectable = isDateSelectable(cur)
                    )
                )
                cur = cur.plusDays(1)
            }
        }

        // Current month days
        var curDay = firstOfMonth
        while (!curDay.isAfter(lastOfMonth)) {
            daysList.add(
                CalendarDay(
                    date = curDay,
                    isCurrentMonth = true,
                    isSelected = curDay == selectedDate,
                    isToday = curDay == today,
                    isSelectable = isDateSelectable(curDay)
                )
            )
            curDay = curDay.plusDays(1)
        }

        // Trailing days to fill the 6-row or 5-row grid (42 days total ensures uniform grid height)
        val remainingDays = 42 - daysList.size
        var nextMonthDay = lastOfMonth.plusDays(1)
        repeat(remainingDays) {
            daysList.add(
                CalendarDay(
                    date = nextMonthDay,
                    isCurrentMonth = false,
                    isSelected = nextMonthDay == selectedDate,
                    isToday = nextMonthDay == today,
                    isSelectable = isDateSelectable(nextMonthDay)
                )
            )
            nextMonthDay = nextMonthDay.plusDays(1)
        }

        return daysList
    }

    companion object {
        val Saver: Saver<CalendarState, Any> = listSaver(
            save = { state ->
                listOf(
                    state.selectedDate?.toString(),
                    state.displayedMonth.toString(),
                    state.viewMode.name,
                    state.minDate?.toString(),
                    state.maxDate?.toString(),
                    state.firstDayOfWeek.name
                )
            },
            restore = { list ->
                val selectedDateStr = list[0] as? String
                val displayedMonthStr = list[1] as? String
                val viewModeStr = list[2] as? String
                val minDateStr = list[3] as? String
                val maxDateStr = list[4] as? String
                val firstDayOfWeekStr = list[5] as? String

                CalendarState(
                    initialSelectedDate = selectedDateStr?.let { LocalDate.parse(it) },
                    initialDisplayedMonth = displayedMonthStr?.let { YearMonth.parse(it) } ?: YearMonth.now(),
                    initialViewMode = viewModeStr?.let { CalendarViewMode.valueOf(it) } ?: CalendarViewMode.DATE_PICKER,
                    minDate = minDateStr?.let { LocalDate.parse(it) },
                    maxDate = maxDateStr?.let { LocalDate.parse(it) },
                    firstDayOfWeek = firstDayOfWeekStr?.let { DayOfWeek.valueOf(it) } ?: WeekFields.of(Locale.getDefault()).firstDayOfWeek
                )
            }
        )
    }
}

/**
 * Creates and remembers a [CalendarState].
 */
@Composable
fun rememberCalendarState(
    initialSelectedDate: LocalDate? = null,
    initialDisplayedMonth: YearMonth = initialSelectedDate?.let { YearMonth.from(it) } ?: YearMonth.now(),
    initialViewMode: CalendarViewMode = CalendarViewMode.DATE_PICKER,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
): CalendarState {
    return rememberSaveable(
        initialSelectedDate,
        initialDisplayedMonth,
        initialViewMode,
        minDate,
        maxDate,
        firstDayOfWeek,
        saver = CalendarState.Saver
    ) {
        CalendarState(
            initialSelectedDate = initialSelectedDate,
            initialDisplayedMonth = initialDisplayedMonth,
            initialViewMode = initialViewMode,
            minDate = minDate,
            maxDate = maxDate,
            firstDayOfWeek = firstDayOfWeek
        )
    }
}
