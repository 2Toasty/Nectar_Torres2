package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.ui.components.DialogTip


@Composable
fun FullScreenSuccessDialog(navController: NavController) {
    // Navegación hacia una nueva pantalla completa sin el `ModalBottomSheet`

    // Estado para mostrar/ocultar el ToolTip
var showDialog by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // Fondo blanco para toda la pantalla
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxHeight()  // Aseguramos que la columna tome toda la altura disponible
        ) {
            Image(
                painter = painterResource(id = R.drawable.checked_out),  // Imagen de éxito
                contentDescription = "Order Accepted",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your Order has been accepted",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold  // Hacer el texto en negrita
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your items have been placed and are on their way to being processed",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))  // Espacio flexible que empuja los botones hacia abajo

            // Botón para rastrear la orden
            Button(
                onClick = { /* Acción para rastrear la orden */

                    showDialog = true  // Activar el tooltip cuando se hace clic en la X


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.medium,

                ) {
                Text(text = "Track Order", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold)
                )
            }



            // Llamar al Dialog para mostrar el mensaje
            DialogTip(showDialog = showDialog, onDismiss = { showDialog = false })



            Spacer(modifier = Modifier.height(16.dp))  // Espacio entre los botones

            // Botón para regresar a Home
            TextButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
        }
    }
}
