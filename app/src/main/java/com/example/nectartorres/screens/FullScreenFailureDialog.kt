package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.R

@Composable
fun FullScreenFailureDialog(navController: NavController) {
    // Fondo oscuro semitransparente que deja ver el contenido detrás (el carrito)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),  // Fondo oscuro que permite ver la pantalla debajo
        contentAlignment = Alignment.Center
    ) {
        // La tarjeta con el mensaje de fallo
        Card(
            shape = RoundedCornerShape(16.dp),  // Esquinas redondeadas
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),  // Fondo blanco de la tarjeta
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f)  // Ocupa el 85% del ancho de la pantalla
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.check_out_fail),  // Imagen de fallo
                    contentDescription = "Order Failed",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Oops! Order Failed",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Something went wrong. Please try again.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón para intentar nuevamente
                Button(
                    onClick = { navController.navigate("cart") },  // Volver al carrito
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),  // Ajuste de altura para coherencia
                    shape = RoundedCornerShape(16.dp),  // Botón rectangular redondeado
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Please Try Again", style = MaterialTheme.typography.bodyLarge)
                }

                // Botón para regresar a Home
                TextButton(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Back to Home", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface))
                }
            }
        }
    }
}