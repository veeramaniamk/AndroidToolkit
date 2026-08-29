package com.tool.calendar.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tool.calendar.model.CalendarViewMode
import com.tool.calendar.state.CalendarState
import com.tool.calendar.theme.CalendarDefaults
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun CalendarLightPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(modifier = Modifier.padding(16.dp)) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now()
                )
            }
            CalendarView(state = state)
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CalendarDarkPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color(0xFF121212)
        ) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now()
                )
            }
            CalendarView(
                state = state,
                colors = CalendarDefaults.colors(
                    containerColor = Color(0xFF1E1E1E),
                    dialogBackgroundColor = Color(0xFF252525)
                )
            )
        }
    }
}

@Preview(name = "Emerald Theme", showBackground = true)
@Composable
fun CalendarEmeraldPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        val state = remember {
            CalendarState(
                initialSelectedDate = LocalDate.now(),
                initialDisplayedMonth = YearMonth.now()
            )
        }
        CalendarView(
            state = state,
            colors = CalendarDefaults.emeraldColors(isDark = false)
        )
    }
}

@Preview(name = "Purple Theme Dark", showBackground = true)
@Composable
fun CalendarPurpleDarkPreview() {
    Surface(
        modifier = Modifier.padding(16.dp),
        color = Color(0xFF181524)
    ) {
        val state = remember {
            CalendarState(
                initialSelectedDate = LocalDate.now(),
                initialDisplayedMonth = YearMonth.now()
            )
        }
        CalendarView(
            state = state,
            colors = CalendarDefaults.purpleColors(isDark = true)
        )
    }
}

@Preview(name = "Sunset Coral Theme", showBackground = true)
@Composable
fun CalendarSunsetPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        val state = remember {
            CalendarState(
                initialSelectedDate = LocalDate.now(),
                initialDisplayedMonth = YearMonth.now()
            )
        }
        CalendarView(
            state = state,
            colors = CalendarDefaults.sunsetColors(isDark = false)
        )
    }
}

@Preview(name = "Month Picker Mode", showBackground = true)
@Composable
fun CalendarMonthPickerPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now(),
                    initialViewMode = CalendarViewMode.MONTH_PICKER
                )
            }
            CalendarView(state = state)
        }
    }
}

@Preview(name = "Year Picker Mode", showBackground = true)
@Composable
fun CalendarYearPickerPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now(),
                    initialViewMode = CalendarViewMode.YEAR_PICKER
                )
            }
            CalendarView(state = state)
        }
    }
}

@Preview(name = "Date Picker Field", showBackground = true)
@Composable
fun CalendarDatePickerFieldPreview() {
    MaterialTheme {
        var date by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
        Surface(modifier = Modifier.padding(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CalendarDatePickerField(
                    selectedDate = date,
                    onDateSelected = { date = it },
                    onClear = { date = null }
                )
            }
        }
    }
}

@Preview(name = "Small Phone (320dp)", widthDp = 320, showBackground = true)
@Composable
fun CalendarSmallPhonePreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(8.dp)) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now()
                )
            }
            CalendarView(
                state = state,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "Tablet (600dp)", widthDp = 600, showBackground = true)
@Composable
fun CalendarTabletPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(24.dp)) {
            val state = remember {
                CalendarState(
                    initialSelectedDate = LocalDate.now(),
                    initialDisplayedMonth = YearMonth.now()
                )
            }
            CalendarView(
                state = state,
                modifier = Modifier.width(440.dp)
            )
        }
    }
}
