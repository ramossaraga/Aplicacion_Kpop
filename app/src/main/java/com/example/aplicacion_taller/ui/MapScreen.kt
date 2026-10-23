package com.example.aplicacion_taller.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen() {
    // Coordenadas de la UAQ (o tu punto de entrega)
    val uaqLocation = LatLng(20.5912, -100.4157)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uaqLocation, 15f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "📍 Punto de Entrega Oficial",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Recoge tu K-Pop Merch en nuestra sucursal asignada.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = false),
            uiSettings = MapUiSettings(zoomControlsEnabled = true)
        ) {
            Marker(
                state = MarkerState(position = uaqLocation),
                title = "Sucursal Central K-Pop",
                snippet = "Punto de entrega oficial"
            )
        }
    }
}