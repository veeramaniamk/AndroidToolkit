# Calendar Module (`:calendar`)

A modern, responsive, and customizable Calendar component for Jetpack Compose (Material 3).

---

## 📦 Dependency & Installation

### Step 1: Add JitPack Repository
Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Calendar Dependency
In your app module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.veeramaniamk.AndroidToolkit:calendar:1.0.2")
}
```

---

## ✨ Features

- **3 Ready-to-Use UI Flavors:**
  - `CalendarView`: Embeddable inline calendar composable.
  - `CalendarDialog`: Animated pop-up modal dialog with spring animations.
  - `CalendarDatePickerField`: Form input field with automatic dialog trigger and clear button.
- **Interactive Gestures:** Smooth horizontal swipe gesture to flip through months.
- **Fast Month & Year Navigation:** Built-in interactive month grid and year selector.
- **Rich Theming:** Full Material 3 theming + preset palettes (`Default`, `Emerald`, `Purple`, `Sunset`).
- **Typography & Font Support:** Easily swap custom `FontFamily` across the entire calendar.
- **Min/Max Date Constraints:** Disable selection of out-of-range dates.
- **Light & Dark Mode:** First-class support for both system themes.

---

## 🚀 Quick Start Examples

### 1. Basic Inline Calendar (`CalendarView`)

Embed the calendar directly inside any screen or column:

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tool.calendar.state.rememberCalendarState
import com.tool.calendar.ui.CalendarView
import java.time.LocalDate

@Composable
fun SimpleCalendarScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val calendarState = rememberCalendarState(initialSelectedDate = selectedDate)

    CalendarView(
        state = calendarState,
        onDateSelected = { date ->
            selectedDate = date
            println("Selected date: $date")
        },
        modifier = Modifier
    )
}
```

---

### 2. Form Input Field (`CalendarDatePickerField`)

Use the form field component for user inputs (e.g. Booking, Birthdays, Appointments):

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tool.calendar.ui.CalendarDatePickerField
import java.time.LocalDate

@Composable
fun BookingDateField() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    CalendarDatePickerField(
        selectedDate = selectedDate,
        onDateSelected = { date -> selectedDate = date },
        onClear = { selectedDate = null },
        label = "Appointment Date",
        placeholder = "Select your date",
        minDate = LocalDate.now(), // Disable past dates
        maxDate = LocalDate.now().plusMonths(3) // Up to 3 months ahead
    )
}
```

---

### 3. Modal Popup Dialog (`CalendarDialog`)

Open the calendar as an animated modal dialog:

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.tool.calendar.ui.CalendarDialog
import java.time.LocalDate

@Composable
fun ModalPickerExample() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Button(onClick = { showDialog = true }) {
        Text(text = selectedDate?.toString() ?: "Pick a Date")
    }

    if (showDialog) {
        CalendarDialog(
            initialDate = selectedDate ?: LocalDate.now(),
            onDateConfirmed = { date ->
                selectedDate = date
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}
```

---

## 🎨 Theming & Customization

The calendar supports full color, typography, and shape overrides via `CalendarDefaults`.

### Using Preset Color Themes
Switch between curated color palettes in one line:

```kotlin
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.ui.CalendarView

@Composable
fun ThemedCalendarExample() {
    val isDark = isSystemInDarkTheme()

    // Choose one:
    val emeraldTheme = CalendarDefaults.emeraldColors(isDark = isDark)
    val purpleTheme = CalendarDefaults.purpleColors(isDark = isDark)
    val sunsetTheme = CalendarDefaults.sunsetColors(isDark = isDark)

    CalendarView(
        colors = emeraldTheme
    )
}
```

---

### Custom Typography & Fonts

Apply your app's custom `FontFamily`:

```kotlin
import androidx.compose.ui.text.font.FontFamily
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.ui.CalendarView

@Composable
fun CustomFontCalendar() {
    CalendarView(
        typography = CalendarDefaults.typography(
            fontFamily = FontFamily.Serif
        )
    )
}
```

---

### Fully Custom Colors

Override specific elements of the calendar:

```kotlin
import androidx.compose.ui.graphics.Color
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.ui.CalendarView

@Composable
fun CustomColorCalendar() {
    val customColors = CalendarDefaults.colors(
        containerColor = Color(0xFF1E1E2E),
        selectedDayBackgroundColor = Color(0xFFFF6B6B),
        selectedDayTextColor = Color.White,
        todayTextColor = Color(0xFF4D96FF),
        todayBorderColor = Color(0xFF4D96FF),
        dayTextColor = Color(0xFFCDD6F4),
        headerTextColor = Color.White
    )

    CalendarView(
        colors = customColors
    )
}
```

---

## 🛠️ API Reference

### 1. `CalendarView`

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `modifier` | `Modifier` | `Modifier` | Layout modifier. |
| `state` | `CalendarState` | `rememberCalendarState()` | Calendar state controller. |
| `colors` | `CalendarColors` | `CalendarDefaults.colors()` | Color palette. |
| `typography` | `CalendarTypography` | `CalendarDefaults.typography()` | Typography styles. |
| `shapes` | `CalendarShapes` | `CalendarDefaults.shapes()` | Corner shapes. |
| `onDateSelected` | `((LocalDate) -> Unit)?` | `null` | Callback triggered when a date is clicked. |
| `showHeader` | `Boolean` | `true` | Show or hide the top header chip bar. |
| `showTodayButton` | `Boolean` | `true` | Show or hide the shortcut button to jump to today. |
| `locale` | `Locale` | `Locale.getDefault()` | Locale used for month & weekday localization. |

---

### 2. `CalendarDatePickerField`

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `selectedDate` | `LocalDate?` | *Required* | Currently selected date. |
| `onDateSelected` | `(LocalDate) -> Unit` | *Required* | Invoked when a date is chosen from the dialog. |
| `label` | `String` | `"Select Date"` | Input field label. |
| `placeholder` | `String` | `"MM/DD/YYYY"` | Placeholder text when empty. |
| `minDate` | `LocalDate?` | `null` | Earliest selectable date. |
| `maxDate` | `LocalDate?` | `null` | Latest selectable date. |
| `isClearable` | `Boolean` | `true` | Shows clear icon when date is selected. |
| `onClear` | `(() -> Unit)?` | `null` | Callback when clear icon is tapped. |
| `enabled` | `Boolean` | `true` | Whether the field is interactive. |

---

### 3. `CalendarDialog`

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `onDismissRequest` | `() -> Unit` | *Required* | Invoked when user taps outside or presses back. |
| `onDateConfirmed` | `(LocalDate) -> Unit` | *Required* | Invoked when user selects a date. |
| `initialDate` | `LocalDate?` | `null` | Starting date selection. |
| `minDate` | `LocalDate?` | `null` | Minimum selectable date. |
| `maxDate` | `LocalDate?` | `null` | Maximum selectable date. |

---

### 4. `CalendarState` Methods

Control the calendar programmatically:

```kotlin
val state = rememberCalendarState()

state.selectDate(LocalDate.of(2026, 12, 25)) // Select date
state.nextMonth()                            // Move 1 month forward
state.previousMonth()                        // Move 1 month backward
state.jumpToToday()                          // Reset to current date
state.toggleMonthPicker()                    // Open month selector grid
state.toggleYearPicker()                     // Open year selector grid
state.clearSelection()                       // Deselect current date
```
