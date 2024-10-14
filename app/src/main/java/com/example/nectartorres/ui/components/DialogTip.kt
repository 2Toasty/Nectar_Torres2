package com.example.nectartorres.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun DialogTip(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        // Iniciar un efecto que cierra el diálogo después de 2 segundos
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(3000)
            onDismiss()  // Cerrar el diálogo después de 2 segundos
        }

        Dialog(onDismissRequest = { onDismiss() }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.4f)),  // Fondo semi-transparente
                contentAlignment = Alignment.Center  // Centrar todo el contenido del diálogo
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            color = Color.DarkGray,  // Fondo oscuro del dialog
                            shape = RoundedCornerShape(16.dp)  // Bordes redondeados
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,  // Centrar el contenido horizontalmente
                        verticalArrangement = Arrangement.Center  // Centrar los elementos dentro de la columna
                    ) {
                        // Botón para cerrar el diálogo manualmente
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                            IconButton(onClick = { onDismiss() }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(0.dp))

                        // Texto de título
                        Text(
                            text = "Disculpe",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White  // Texto claro
                            ),
                            textAlign = TextAlign.Center  // Centrar el texto
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Texto de descripción
                        Text(
                            text = "Este clickeable aún no tiene funcionalidad.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White  // Texto claro
                            ),
                            textAlign = TextAlign.Center  // Centrar el texto
                        )
                    }
                }
            }
        }
    }
}
