package com.example.aplicacion_taller.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacion_taller.model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit = {},
    cartItems: List<CartItem> = emptyList(),
    onRemoveItem: (CartItem) -> Unit = {},
    onQuantityChange: (CartItem, Int) -> Unit = { _, _ -> },
    onCheckoutClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val total = cartItems.sumOf { it.price * it.quantity }

    // Función Intent para abrir la sucursal en Google Maps
    fun openGoogleMaps(locationQuery: String) {
        val query = if (locationQuery.isNotBlank()) locationQuery else "Universidad Autónoma de Querétaro"
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}"))
            context.startActivity(webIntent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras 🛒") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Total:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "$${String.format("%.2f", total)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Botón para abrir Google Maps
                        OutlinedButton(
                            onClick = { openGoogleMaps("Universidad Autónoma de Querétaro") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicación",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Ver Punto de Entrega en Google Maps")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = onCheckoutClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Finalizar Compra 🫰", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tu carrito de merch está vacío 💔",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "$${String.format("%.2f", item.price)} c/u",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Botón Disminuir
                                    OutlinedButton(
                                        onClick = { onQuantityChange(item, item.quantity - 1) },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Text("-")
                                    }

                                    Text(
                                        text = "${item.quantity}",
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        fontWeight = FontWeight.Bold
                                    )

                                    // Botón Aumentar
                                    OutlinedButton(
                                        onClick = { onQuantityChange(item, item.quantity + 1) },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Text("+")
                                    }

                                    // Botón ELIMINAR ARTÍCULO
                                    IconButton(onClick = { onRemoveItem(item) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}