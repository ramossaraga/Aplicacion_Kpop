package com.example.aplicacion_taller.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplicacion_taller.ui.components.AppBottomBar
import com.example.aplicacion_taller.ui.components.AppTopBar
import com.example.aplicacion_taller.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Scaffold(
        topBar = { AppTopBar(title = "APP TALLER") },
        bottomBar = {
            AppBottomBar(
                selectedIndex = viewModel.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val texto = when (viewModel.selectedTab) {
                0 -> "Pantalla de Inicio"
                1 -> "Pantalla de Perfil"
                else -> "Pantalla de Ajustes"
            }
            Text(text = texto, fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}