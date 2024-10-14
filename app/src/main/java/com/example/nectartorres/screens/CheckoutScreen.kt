package com.example.nectartorres.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



import androidx.compose.animation.AnimatedVisibility


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    totalAmount: Double
) {
    var deliveryMethod by remember { mutableStateOf("Select Method") }
    var promoCode by remember { mutableStateOf("Pick discount") }
    var showModal by remember { mutableStateOf(true) }

    // Contenedor principal de la pantalla
    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo oscuro transparente que no es clickeable para cerrar el modal
        if (showModal) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Fondo oscuro detrás de la tarjeta
            )
        }

        // Animación para la tarjeta de Checkout
        AnimatedVisibility(
            visible = showModal,
            enter = slideInVertically(
                initialOffsetY = { it }, // Aparece desde la parte inferior de la pantalla
                animationSpec = tween(500) // Duración de la animación
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }, // Desaparece hacia la parte inferior de la pantalla
                animationSpec = tween(500)
            )
        ) {
            // Tarjeta del Checkout que ocupa la mitad de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 250.dp, max = 450.dp) // Ajusta según el contenido
                    .align(Alignment.BottomCenter) // Alineado con la parte inferior
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Comprimir verticalmente
                ) {
                    // Header con el título y el botón de cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Checkout",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        IconButton(onClick = {
                            showModal = false // Cerrar el modal al hacer clic en la X
                            navController.popBackStack() // Volver a la pantalla anterior al cerrar
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    // Divider entre el header y las opciones
                    HorizontalDivider()

                    // Opciones de Delivery
                    CheckoutOptionItem(
                        label = "Delivery",
                        value = deliveryMethod,
                        onClick = { /* Acción para seleccionar método de entrega */ }
                    )

                    // Opciones de Payment
                    CheckoutOptionItem(
                        label = "Payment",
                        value = "",
                        onClick = { /* Acción para seleccionar método de pago */ },
                        paymentIcon = Icons.Default.Payment
                    )

                    // Opciones de Promo Code
                    CheckoutOptionItem(
                        label = "Promo Code",
                        value = promoCode,
                        onClick = { /* Acción para seleccionar código promocional */ }
                    )

                    // Total Cost
                    CheckoutOptionItem(
                        label = "Total Cost",
                        value = "$${"%.2f".format(totalAmount)}",
                        onClick = { /* Acción cuando se haga clic en total cost */ },
                        isTotalCost = true
                    )

                    Spacer(modifier = Modifier.height(3.dp)) // Espacio ajustable entre los elementos

                    // Términos y Condiciones
                    TermsAndConditions()

                    Spacer(modifier = Modifier.height(5.dp)) // Espacio ajustable antes del botón

                    // Botón de Place Order
                    PlaceOrderButton(onClick = {
                        if (Math.random() > 0.5) {
                            navController.navigate("success")
                        } else {
                            navController.navigate("failure")
                        }
                    })
                }
            }
        }
    }
}


@Composable
fun CheckoutOptionItem(label: String, value: String, onClick: () -> Unit, paymentIcon: ImageVector? = null, isTotalCost: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 1.dp),  // Comprimimos el espacio vertical
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray))

        if (isTotalCost) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (paymentIcon != null) {
                    Icon(paymentIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp)) // Margen reducido
}

@Composable
fun PlaceOrderButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Place Order",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TermsAndConditions() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "By placing an order you agree to our",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        )
        Text(
            buildAnnotatedString {
                append("Terms ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))) {
                    append("and ")
                }
                append("Conditions")
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}



