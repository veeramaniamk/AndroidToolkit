package com.tool.calendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tool.calendar.state.CalendarState
import com.tool.calendar.state.rememberCalendarState
import com.tool.calendar.theme.CalendarColors
import com.tool.calendar.theme.CalendarDefaults
import com.tool.calendar.theme.CalendarShapes
import com.tool.calendar.theme.CalendarTypography
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Animated Modal Dialog wrapper for the Calendar picker.
 */
@Composable
fun CalendarDialog(
    onDismissRequest: () -> Unit,
    onDateConfirmed: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
    typography: CalendarTypography = CalendarDefaults.typography(),
    shapes: CalendarShapes = CalendarDefaults.shapes(),
    title: String = "Select Date",
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    locale: Locale = Locale.getDefault()
) {
    val state = rememberCalendarState(
        initialSelectedDate = initialDate,
        minDate = minDate,
        maxDate = maxDate
    )

    var isVisible by remember { mutableStateOf(false) }

    // Trigger smooth enter animation after composition
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val dismissWithAnimation: () -> Unit = {
        isVisible = false
    }

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            delay(180) // wait for exit animation
            onDismissRequest()
        }
    }

    Dialog(
        onDismissRequest = dismissWithAnimation,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = dismissWithAnimation
                )
                .padding(horizontal = 16.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(220)) +
                        scaleIn(
                            initialScale = 0.85f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ),
                exit = fadeOut(animationSpec = tween(160)) +
                        scaleOut(
                            targetScale = 0.85f,
                            animationSpec = tween(160, easing = FastOutSlowInEasing)
                        )
            ) {
                Surface(
                    modifier = modifier
                        .widthIn(min = 280.dp, max = 400.dp)
                        .fillMaxWidth(0.92f)
                        .wrapContentHeight()
                        .clip(shapes.dialogShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { /* prevent click through to scrim */ }
                        ),
                    color = colors.dialogBackgroundColor,
                    shape = shapes.dialogShape,
                    shadowElevation = 8.dp,
                    tonalElevation = 6.dp
                ) {

                    // Inline Calendar Component
                    CalendarView(
                        state = state,
                        colors = colors,
                        typography = typography,
                        shapes = shapes,
                        locale = locale,
                        onDateSelected = {
                            onDateConfirmed(it)
                            dismissWithAnimation()
                        },
                        showTodayButton = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }
        }
    }
}
