package com.tool.calendar.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Reusable DatePicker input field that opens the animated CalendarDialog upon click.
 */
@Composable
fun CalendarDatePickerField(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Select Date",
    placeholder: String = "MM/DD/YYYY",
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
    typography: CalendarTypography = CalendarDefaults.typography(),
    shapes: CalendarShapes = CalendarDefaults.shapes(),
    dialogTitle: String = "Select Date",
    formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM),
    isClearable: Boolean = true,
    onClear: (() -> Unit)? = null,
    enabled: Boolean = true,
    locale: Locale = Locale.getDefault()
) {
    var showDialog by remember { mutableStateOf(false) }

    val formattedDateText = remember(selectedDate, formatter, locale) {
        selectedDate?.format(formatter.withLocale(locale)) ?: ""
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = formattedDateText,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(label, style = typography.headerChipStyle) },
            placeholder = { Text(placeholder, style = typography.dayStyle) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    tint = colors.todayTextColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (isClearable && selectedDate != null && enabled) {
                    IconButton(
                        onClick = { onClear?.invoke() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Date",
                            tint = colors.headerIconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = shapes.pickerItemShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.selectedDayBackgroundColor,
                unfocusedBorderColor = colors.dividerColor,
                focusedLabelColor = colors.selectedDayBackgroundColor,
                unfocusedLabelColor = colors.headerTextColor.copy(alpha = 0.7f),
                focusedTextColor = colors.dayTextColor,
                unfocusedTextColor = colors.dayTextColor,
                focusedContainerColor = colors.containerColor,
                unfocusedContainerColor = colors.containerColor
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        // Overlay transparent clickable box to reliably intercept clicks on readOnly field
        if (enabled) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(shapes.pickerItemShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { showDialog = true }
                    )
            )
        }
    }

    if (showDialog) {
        CalendarDialog(
            onDismissRequest = { showDialog = false },
            onDateConfirmed = { date ->
                onDateSelected(date)
                showDialog = false
            },
            initialDate = selectedDate,
            minDate = minDate,
            maxDate = maxDate,
            colors = colors,
            typography = typography,
            shapes = shapes,
            title = dialogTitle,
            locale = locale
        )
    }
}
