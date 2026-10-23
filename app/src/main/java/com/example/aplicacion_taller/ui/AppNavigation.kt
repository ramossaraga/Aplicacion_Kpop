package com.example.aplicacion_taller.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacion_taller.model.CartItem
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Lista reactiva para el carrito de K-Pop Merch
    val cartItems = remember {
        mutableStateListOf(
            CartItem("1", "Lightstick Oficial", 1200.0, 1, "Sucursal Centro K-Pop"),
            CartItem("2", "Pack Photocards K-Pop", 350.0, 2, "Sucursal Antea")
        )
    }

    // Función para agregar o sumar productos
    fun addToCart(name: String, priceDouble: Double) {
        val existingIndex = cartItems.indexOfFirst { it.name == name }
        if (existingIndex != -1) {
            val item = cartItems[existingIndex]
            cartItems[existingIndex] = item.copy(quantity = item.quantity + 1)
        } else {
            cartItems.add(
                CartItem(
                    id = System.currentTimeMillis().toString(),
                    name = name,
                    price = priceDouble,
                    quantity = 1,
                    locationName = "Sucursal Principal K-Pop"
                )
            )
        }
    }

    val startDestination = if (currentUser != null) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onAddToCart = { name, price -> addToCart(name, price) }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                cartItems = cartItems,
                onRemoveItem = { itemToRemove ->
                    cartItems.remove(itemToRemove) // Elimina el artículo directamente
                },
                onQuantityChange = { itemToUpdate, newQuantity ->
                    val index = cartItems.indexOf(itemToUpdate)
                    if (index != -1) {
                        if (newQuantity <= 0) {
                            cartItems.removeAt(index) // Si baja de 1, lo borra
                        } else {
                            cartItems[index] = itemToUpdate.copy(quantity = newQuantity)
                        }
                    }
                },
                onCheckoutClick = {
                    cartItems.clear() // Vacía el carrito al procesar la compra
                    navController.popBackStack()
                }
            )
        }
    }
}