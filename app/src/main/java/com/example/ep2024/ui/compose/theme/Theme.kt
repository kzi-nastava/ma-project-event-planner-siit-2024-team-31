package com.example.ep2024.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape

private val LightColors = lightColorScheme(
    primary = Color(0xFF2563EB), // Tailwind blue-600
    onPrimary = Color.White,
    secondary = Color(0xFF7C3AED), // violet-600
    onSecondary = Color.White,
    tertiary = Color(0xFFF97316), // orange-500
    onTertiary = Color.White,
    background = Color(0xFFF1F5F9), // slate-100
    onBackground = Color(0xFF0F172A), // slate-900
    surface = Color.White,
    onSurface = Color(0xFF1E293B),
    surfaceVariant = Color(0xFFE2E8F0), // slate-200
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFF94A3B8)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF3B82F6),
    onPrimary = Color(0xFF0F172A),
    secondary = Color(0xFF8B5CF6),
    onSecondary = Color(0xFF1E1E1E),
    tertiary = Color(0xFFFFA454),
    onTertiary = Color(0xFF1E1E1E),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF64748B)
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4),
    small = RoundedCornerShape(8),
    medium = RoundedCornerShape(12),
    large = RoundedCornerShape(16),
    extraLarge = RoundedCornerShape(28)
)

// Keeping default Typography for brevity; can map custom fonts if needed
private val AppTypography = Typography()

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
