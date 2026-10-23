package com.example.aplicacion_taller.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KpopPinkColorScheme = lightColorScheme(
    primary = Color(0xFFFF85A1),          // Rosa acento
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFF0F5), // Fondo tarjetas rosa suave
    onPrimaryContainer = Color(0xFF8B263E),// Texto títulos en tarjetas
    secondary = Color(0xFFF7CAD0),
    onSecondary = Color(0xFF4A1525),
    background = Color(0xFFFFF9FA),       // Fondo pantalla rosado
    onBackground = Color(0xFF2D1E2F),
    surface = Color.White,
    onSurface = Color(0xFF2D1E2F)
)

@Composable
fun Aplicacion_TallerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KpopPinkColorScheme,
        typography = AestheticTypography, // 👈 Aplica la tipografía redondeada
        content = content
    )
}