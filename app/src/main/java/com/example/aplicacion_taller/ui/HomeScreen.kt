package com.example.aplicacion_taller.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onAddToCart: (String, Double) -> Unit = { _, _ -> }
) {
    val auth = FirebaseAuth.getInstance()
    val userEmail = auth.currentUser?.email ?: "K-Pop Fan ✨"
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (selectedTab) {
                            0 -> "K-Pop Merch Store 🫰"
                            1 -> "Ofertas Especiales 🔥"
                            2 -> "Ubicación de Sucursal 📍"
                            else -> "Mi Perfil K-Pop 👤"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                    IconButton(onClick = {
                        auth.signOut()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Salir")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Ofertas") },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Ofertas") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    label = { Text("Mapa") },
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Mapa") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    label = { Text("Perfil") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> InicioTabContent(onAddToCart = onAddToCart, onGoToCart = { navController.navigate(Screen.Cart.route) })
                1 -> OfertasTabContent(onAddToCart = onAddToCart, onGoToCart = { navController.navigate(Screen.Cart.route) })
                2 -> MapScreen()
                3 -> PerfilTabContent(userEmail = userEmail, auth = auth, navController = navController)
            }
        }
    }
}

@Composable
fun InicioTabContent(onAddToCart: (String, Double) -> Unit, onGoToCart: () -> Unit) {
    val productos = listOf(
        "Lightstick Oficial" to 1200.0,
        "Photocard Set Edición Especial" to 350.0,
        "Álbum Firmado Autografiado" to 850.0,
        "Picket Oficial Tour" to 250.0,
        "Photostrip & Sticker Pack" to 150.0
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Catálogo Destacado ✨", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(productos) { (nombre, precio) ->
                ItemCard(
                    nombre = nombre,
                    precio = "$${precio}",
                    onAdd = {
                        onAddToCart(nombre, precio)
                        onGoToCart()
                    }
                )
            }
        }
    }
}

@Composable
fun OfertasTabContent(onAddToCart: (String, Double) -> Unit, onGoToCart: () -> Unit) {
    val ofertas = listOf(
        "Lightstick Ver. 1 (Últimas piezas)" to 890.0,
        "Mini Photocard Binder" to 199.0,
        "Keychains Acrílicos GL & K-Pop" to 99.0
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "⚡ Ofertas por Tiempo Limitado", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(ofertas) { (nombre, precio) ->
                ItemCard(
                    nombre = nombre,
                    precio = "$${precio}",
                    onAdd = {
                        onAddToCart(nombre, precio)
                        onGoToCart()
                    }
                )
            }
        }
    }
}

@Composable
fun PerfilTabContent(userEmail: String, auth: FirebaseAuth, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Perfil del Usuario 🎀", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = userEmail, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(Screen.Cart.route) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Ver Mis Pedidos / Carrito 🛒", modifier = Modifier.padding(vertical = 4.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                auth.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cerrar Sesión 🚪", modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Composable
fun ItemCard(nombre: String, precio: String, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = precio,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp
                )
            }
            Button(
                onClick = onAdd,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Agregar ✨")
            }
        }
    }
}