package com.example.aplicacion_taller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aplicacion_taller.ui.AppNavigation
import com.example.aplicacion_taller.ui.theme.Aplicacion_TallerTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asegura que Firebase esté inicializado antes de cargar la navegación
        FirebaseApp.initializeApp(this)

        setContent {
            // Aplicamos nuestro tema K-Pop rosa pastel
            Aplicacion_TallerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                }
            }
        }
    }
}