package com.example.aplicacion_taller.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Cart : Screen("cart")

    // Rutas para la Barra de Navegación Inferior (Bottom Navigation)
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Offers : Screen("offers", "Ofertas", Icons.Default.LocalOffer)
    object Promotions : Screen("promotions", "Promociones", Icons.Default.Star)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
}