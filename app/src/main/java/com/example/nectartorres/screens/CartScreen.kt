package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu
import com.example.nectartorres.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close

import androidx.compose.material.icons.filled.Remove

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.sp
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.viewmodels.ProductViewModel
import com.example.nectartorres.ui.components.DialogTip


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, authViewModel: AuthViewModel) {  // Usamos AuthViewModel aquí
    val cartItems = authViewModel.cartItems
    val isLoading = authViewModel.isLoading
    var quantities by remember { mutableStateOf(cartItems.map { it.id to 1 }.toMap()) }  // Mapa para manejar la cantidad de cada producto
    var totalAmount by remember { mutableStateOf(0.0) }  // Valor total del carrito calculado localmente
    val coroutineScope = rememberCoroutineScope()

    // Actualizar el totalAmount cuando se cambia la cantidad de productos
    fun updateTotalAmount() {
        totalAmount = cartItems.sumOf { it.price * (quantities[it.id] ?: 1) }
    }

    // Cargar un carrito aleatorio usando la función proporcionada en el AuthViewModel
    LaunchedEffect(Unit) {
        authViewModel.loadRandomCart  {
            updateTotalAmount() // Actualiza el total al cargar el carrito
        }
    }

    Scaffold(
        topBar = { TopAppBarWithMenu(title = "My Cart", onMenuClick = { /* Lógica del menú */ }) },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No items in the cart", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Línea divisoria entre la topbar y los productos
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Permite que el botón de checkout esté fijo en la parte inferior
                    ) {
                        items(cartItems) { product ->
                            CartProductItem(
                                product = product,
                                quantities = quantities,
                                onUpdateQuantity = { id, newQuantity ->
                                    quantities = quantities.toMutableMap().also {
                                        it[id] = newQuantity
                                    }
                                    updateTotalAmount()
                                },
                                onRemoveItem = {
                                }
                            )
                        }
                    }


                    // Botón "Go to Checkout"
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = {coroutineScope.launch{
                                navController.navigate("checkout/${"%.2f".format(totalAmount)}")
                            }},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Go to Checkout",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                                )

                                // Precio en un rectángulo más pequeño con relleno
                                Box(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text(text = "$${"%.2f".format(totalAmount)}")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}



@Composable
fun CartProductItem(
    product: Product,
    quantities: Map<Int, Int>,
    onUpdateQuantity: (Int, Int) -> Unit,
    onRemoveItem: (Int) -> Unit  // Agregar acción para eliminar
) {

    // Estado para mostrar/ocultar el ToolTip
    var showDialog by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del producto
        Image(
            painter = rememberAsyncImagePainter(model = product.image),
            contentDescription = product.title,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Nombre del producto y cantidad (columna izquierda)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                maxLines = 2,  // Limitar el nombre a 2 renglones
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Botón de disminuir cantidad
                IconButton(
                    onClick = { if (quantities[product.id] ?: 1 > 1) onUpdateQuantity(product.id, (quantities[product.id] ?: 1) - 1) },
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                        .size(24.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                }

                Text(
                    text = "${quantities[product.id] ?: 1}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Botón de aumentar cantidad
                IconButton(
                    onClick = { onUpdateQuantity(product.id, (quantities[product.id] ?: 1) + 1) },
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                        .size(24.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Columna para la "X" y el precio (columna derecha)
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de eliminar (cruz "X"), alineado a la derecha
            IconButton(
                onClick = {

                    showDialog = true  // Activar el tooltip cuando se hace clic en la X


                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove item",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
            }





            // Precio del producto multiplicado por la cantidad
            Text(
                text = "$${"%.2f".format(product.price * (quantities[product.id] ?: 1))}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    // Llamar al Dialog para mostrar el mensaje
    DialogTip(showDialog = showDialog, onDismiss = { showDialog = false })

    // Divider entre productos
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.primary
    )
}






