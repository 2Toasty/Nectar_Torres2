package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.ui.components.DropdownMenuSample
import com.example.nectartorres.viewmodels.AuthViewModel
import com.example.nectartorres.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectionScreen(navController: NavController, authViewModel: AuthViewModel, productViewModel: ProductViewModel) {
    var selectedZone by remember { mutableStateOf("Buenos Aires") }
    var selectedArea by remember { mutableStateOf("Little Horse") }

    val zones = listOf("Buenos Aires", "Tierra del Fuego", "Salta")
    val areas = listOf("Little Horse", "Chacabuque Park", "Go out if you can")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Imagen más grande
                Image(
                    painter = painterResource(id = R.drawable.map_illustration),
                    contentDescription = "Map",
                    modifier = Modifier.size(180.dp)  // Ajuste de tamaño
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Texto debajo de la imagen
                Text(
                    text = "Select Your Location",
                    style = MaterialTheme.typography.headlineMedium,  // Ajuste de estilo
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Switch on your location to stay in tune with what's happening in your area",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),  // Ligero ajuste de opacidad para un estilo más suave
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp)  // Añadimos margen horizontal para el texto
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Dropdowns para seleccionar zona y área
                Text(
                    text = "Your Zone",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.Gray
                )
                DropdownMenuSample(
                    label = "",
                    items = zones,
                    selectedItem = selectedZone,
                    onItemSelected = { selectedZone = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your Area",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.Gray
                )
                DropdownMenuSample(
                    label = "",
                    items = areas,
                    selectedItem = selectedArea,
                    onItemSelected = { selectedArea = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        // Cargar productos después de seleccionar ubicación usando ProductViewModel
                        productViewModel.loadProducts()
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.medium // Puntas redondeadas
                ) {
                    Text(text = "Submit")
                }
            }
        }
    )
}
