package com.example.aplicacion_taller.model

data class CartItem(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val locationName: String = "" // Relación con la ubicación/mapa
)
