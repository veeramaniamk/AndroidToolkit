package com.tool.calendar.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Defines the colors used across the Calendar component.
 */
@Immutable
data class CalendarColors(
    val containerColor: Color,
    val headerTextColor: Color,
    val headerIconColor: Color,
    val headerChipBackgroundColor: Color,
    val headerChipSelectedBackgroundColor: Color,
    val headerChipTextColor: Color,
    val headerChipSelectedTextColor: Color,
    val weekdayTextColor: Color,
    val dayTextColor: Color,
    val selectedDayTextColor: Color,
    val selectedDayBackgroundColor: Color,
    val todayTextColor: Color,
    val todayBorderColor: Color,
    val adjacentMonthDayTextColor: Color,
    val disabledDayTextColor: Color,
    val pickerItemTextColor: Color,
    val pickerItemSelectedTextColor: Color,
    val pickerItemSelectedBackgroundColor: Color,
    val pickerItemHoverBackgroundColor: Color,
    val dividerColor: Color,
    val dialogBackgroundColor: Color
)

/**
 * Defines the typography styles used across the Calendar component.
 */
@Immutable
data class CalendarTypography(
    val headerTitleStyle: TextStyle,
    val headerChipStyle: TextStyle,
    val weekdayStyle: TextStyle,
    val dayStyle: TextStyle,
    val pickerItemStyle: TextStyle,
    val actionButtonStyle: TextStyle
)

/**
 * Defines the shapes used across the Calendar component.
 */
@Immutable
data class CalendarShapes(
    val containerShape: Shape,
    val headerChipShape: Shape,
    val dayShape: Shape,
    val pickerItemShape: Shape,
    val dialogShape: Shape
)

/**
 * Default configurations, themes, and presets for the Calendar.
 */
object CalendarDefaults {

    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        headerTextColor: Color = MaterialTheme.colorScheme.onSurface,
        headerIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        headerChipBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        headerChipSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
        headerChipTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        headerChipSelectedTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        weekdayTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        dayTextColor: Color = MaterialTheme.colorScheme.onSurface,
        selectedDayTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        selectedDayBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        todayTextColor: Color = MaterialTheme.colorScheme.primary,
        todayBorderColor: Color = MaterialTheme.colorScheme.primary,
        adjacentMonthDayTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
        disabledDayTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        pickerItemTextColor: Color = MaterialTheme.colorScheme.onSurface,
        pickerItemSelectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        pickerItemSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        pickerItemHoverBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        dividerColor: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        dialogBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
    ): CalendarColors {
        return CalendarColors(
            containerColor = containerColor,
            headerTextColor = headerTextColor,
            headerIconColor = headerIconColor,
            headerChipBackgroundColor = headerChipBackgroundColor,
            headerChipSelectedBackgroundColor = headerChipSelectedBackgroundColor,
            headerChipTextColor = headerChipTextColor,
            headerChipSelectedTextColor = headerChipSelectedTextColor,
            weekdayTextColor = weekdayTextColor,
            dayTextColor = dayTextColor,
            selectedDayTextColor = selectedDayTextColor,
            selectedDayBackgroundColor = selectedDayBackgroundColor,
            todayTextColor = todayTextColor,
            todayBorderColor = todayBorderColor,
            adjacentMonthDayTextColor = adjacentMonthDayTextColor,
            disabledDayTextColor = disabledDayTextColor,
            pickerItemTextColor = pickerItemTextColor,
            pickerItemSelectedTextColor = pickerItemSelectedTextColor,
            pickerItemSelectedBackgroundColor = pickerItemSelectedBackgroundColor,
            pickerItemHoverBackgroundColor = pickerItemHoverBackgroundColor,
            dividerColor = dividerColor,
            dialogBackgroundColor = dialogBackgroundColor
        )
    }

    /**
     * Emerald Teal Theme Preset
     */
    @Composable
    fun emeraldColors(
        isDark: Boolean = false
    ): CalendarColors {
        val primary = if (isDark) Color(0xFF4ADE80) else Color(0xFF059669)
        val onPrimary = if (isDark) Color(0xFF064E3B) else Color.White
        val primaryContainer = if (isDark) Color(0xFF065F46) else Color(0xFFD1FAE5)
        val onPrimaryContainer = if (isDark) Color(0xFFA7F3D0) else Color(0xFF064E3B)
        val surface = if (isDark) Color(0xFF131D1A) else Color(0xFFFAFDFA)
        val onSurface = if (isDark) Color(0xFFE2E8F0) else Color(0xFF1E293B)
        val surfaceVariant = if (isDark) Color(0xFF1E2F2B) else Color(0xFFE2EFEA)

        return colors(
            containerColor = surface,
            headerTextColor = onSurface,
            headerIconColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            headerChipBackgroundColor = surfaceVariant,
            headerChipSelectedBackgroundColor = primaryContainer,
            headerChipTextColor = if (isDark) Color(0xFFCBD5E1) else Color(0xFF334155),
            headerChipSelectedTextColor = onPrimaryContainer,
            weekdayTextColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
            dayTextColor = onSurface,
            selectedDayTextColor = onPrimary,
            selectedDayBackgroundColor = primary,
            todayTextColor = primary,
            todayBorderColor = primary,
            adjacentMonthDayTextColor = if (isDark) Color(0xFF475569) else Color(0xFFCBD5E1),
            disabledDayTextColor = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0),
            pickerItemTextColor = onSurface,
            pickerItemSelectedTextColor = onPrimary,
            pickerItemSelectedBackgroundColor = primary,
            pickerItemHoverBackgroundColor = surfaceVariant,
            dividerColor = if (isDark) Color(0xFF1F352E) else Color(0xFFE2E8F0),
            dialogBackgroundColor = if (isDark) Color(0xFF1A2623) else Color(0xFFFFFFFF)
        )
    }

    /**
     * Royal Purple Theme Preset
     */
    @Composable
    fun purpleColors(
        isDark: Boolean = false
    ): CalendarColors {
        val primary = if (isDark) Color(0xFFA855F7) else Color(0xFF7C3AED)
        val onPrimary = Color.White
        val primaryContainer = if (isDark) Color(0xFF581C87) else Color(0xFFEDE9FE)
        val onPrimaryContainer = if (isDark) Color(0xFFDDD6FE) else Color(0xFF4C1D95)
        val surface = if (isDark) Color(0xFF181524) else Color(0xFFFAF9FD)
        val onSurface = if (isDark) Color(0xFFF1F0F7) else Color(0xFF1E1B2E)
        val surfaceVariant = if (isDark) Color(0xFF262038) else Color(0xFFEBE6F8)

        return colors(
            containerColor = surface,
            headerTextColor = onSurface,
            headerIconColor = if (isDark) Color(0xFFA5A0BC) else Color(0xFF6B6488),
            headerChipBackgroundColor = surfaceVariant,
            headerChipSelectedBackgroundColor = primaryContainer,
            headerChipTextColor = if (isDark) Color(0xFFD4D0E6) else Color(0xFF484265),
            headerChipSelectedTextColor = onPrimaryContainer,
            weekdayTextColor = if (isDark) Color(0xFFA5A0BC) else Color(0xFF6B6488),
            dayTextColor = onSurface,
            selectedDayTextColor = onPrimary,
            selectedDayBackgroundColor = primary,
            todayTextColor = primary,
            todayBorderColor = primary,
            adjacentMonthDayTextColor = if (isDark) Color(0xFF4B4566) else Color(0xFFCCC7DF),
            disabledDayTextColor = if (isDark) Color(0xFF332D48) else Color(0xFFE5E2F0),
            pickerItemTextColor = onSurface,
            pickerItemSelectedTextColor = onPrimary,
            pickerItemSelectedBackgroundColor = primary,
            pickerItemHoverBackgroundColor = surfaceVariant,
            dividerColor = if (isDark) Color(0xFF2E2745) else Color(0xFFE7E3F3),
            dialogBackgroundColor = if (isDark) Color(0xFF201B30) else Color(0xFFFFFFFF)
        )
    }

    /**
     * Coral Sunset Theme Preset
     */
    @Composable
    fun sunsetColors(
        isDark: Boolean = false
    ): CalendarColors {
        val primary = if (isDark) Color(0xFFFB7185) else Color(0xFFE11D48)
        val onPrimary = Color.White
        val primaryContainer = if (isDark) Color(0xFF881337) else Color(0xFFFFE4E6)
        val onPrimaryContainer = if (isDark) Color(0xFFFECDD3) else Color(0xFF9F1239)
        val surface = if (isDark) Color(0xFF1E1517) else Color(0xFFFFFDFC)
        val onSurface = if (isDark) Color(0xFFF6ECEE) else Color(0xFF291E21)
        val surfaceVariant = if (isDark) Color(0xFF322025) else Color(0xFFFCE7EC)

        return colors(
            containerColor = surface,
            headerTextColor = onSurface,
            headerIconColor = if (isDark) Color(0xFFB59B9F) else Color(0xFF886368),
            headerChipBackgroundColor = surfaceVariant,
            headerChipSelectedBackgroundColor = primaryContainer,
            headerChipTextColor = if (isDark) Color(0xFFDEC3C8) else Color(0xFF644449),
            headerChipSelectedTextColor = onPrimaryContainer,
            weekdayTextColor = if (isDark) Color(0xFFB59B9F) else Color(0xFF886368),
            dayTextColor = onSurface,
            selectedDayTextColor = onPrimary,
            selectedDayBackgroundColor = primary,
            todayTextColor = primary,
            todayBorderColor = primary,
            adjacentMonthDayTextColor = if (isDark) Color(0xFF553D42) else Color(0xFFDFC6CC),
            disabledDayTextColor = if (isDark) Color(0xFF3B272B) else Color(0xFFF3E2E6),
            pickerItemTextColor = onSurface,
            pickerItemSelectedTextColor = onPrimary,
            pickerItemSelectedBackgroundColor = primary,
            pickerItemHoverBackgroundColor = surfaceVariant,
            dividerColor = if (isDark) Color(0xFF3B2429) else Color(0xFFF4DFE4),
            dialogBackgroundColor = if (isDark) Color(0xFF26191C) else Color(0xFFFFFFFF)
        )
    }

    @Composable
    fun typography(
        fontFamily: FontFamily? = null,
        headerTitleStyle: TextStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.15.sp,
            fontFamily = fontFamily
        ),
        headerChipStyle: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
            fontFamily = fontFamily
        ),
        weekdayStyle: TextStyle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            fontFamily = fontFamily
        ),
        dayStyle: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
            fontFamily = fontFamily
        ),
        pickerItemStyle: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
            fontFamily = fontFamily
        ),
        actionButtonStyle: TextStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            fontFamily = fontFamily
        )
    ): CalendarTypography {
        return CalendarTypography(
            headerTitleStyle = headerTitleStyle,
            headerChipStyle = headerChipStyle,
            weekdayStyle = weekdayStyle,
            dayStyle = dayStyle,
            pickerItemStyle = pickerItemStyle,
            actionButtonStyle = actionButtonStyle
        )
    }

    fun shapes(
        containerShape: Shape = RoundedCornerShape(24.dp),
        headerChipShape: Shape = RoundedCornerShape(12.dp),
        dayShape: Shape = CircleShape,
        pickerItemShape: Shape = RoundedCornerShape(14.dp),
        dialogShape: Shape = RoundedCornerShape(28.dp)
    ): CalendarShapes {
        return CalendarShapes(
            containerShape = containerShape,
            headerChipShape = headerChipShape,
            dayShape = dayShape,
            pickerItemShape = pickerItemShape,
            dialogShape = dialogShape
        )
    }
}

val LocalCalendarColors = staticCompositionLocalOf<CalendarColors> {
    error("No CalendarColors provided")
}

val LocalCalendarTypography = staticCompositionLocalOf<CalendarTypography> {
    error("No CalendarTypography provided")
}

val LocalCalendarShapes = staticCompositionLocalOf<CalendarShapes> {
    error("No CalendarShapes provided")
}
