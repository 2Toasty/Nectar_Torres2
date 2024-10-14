package com.example.nectartorres.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu
import com.example.nectartorres.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(navController: NavController, authViewModel: AuthViewModel) {
    val favoriteProducts = authViewModel.favorites  // Usamos el estado de favoritos del AuthViewModel
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.loadFavorites()
    }

    Scaffold(
        topBar = { TopAppBarWithMenu(title = "Favorites", onMenuClick = { /* Lógica del menú */ }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
            // Lista de productos favoritos
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)  // El contenido toma el espacio disponible
            ) {
                items(favoriteProducts) { product ->
                    FavoriteProductItem(
                        product = product,
                        navController = navController
                    )
                }
            }

            // Botón "Add All to Cart"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (favoriteProducts.isNotEmpty()) {
                                authViewModel.clearFavorites()  // Lógica para limpiar favoritos
                                dialogMessage = "The products have been sent to the cart ;)"
                            } else {
                                dialogMessage = "No products in your favorites."
                            }
                            showDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Add All to Cart",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                    )
                }
            }

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    // Auto dismiss the dialog after 2 seconds
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        showDialog = false
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)  // Fondo transparente
                            .clickable { showDialog = false },  // Cerrar al hacer clic fuera del diálogo
                        contentAlignment = Alignment.Center
                    ) {
                        // Ventana cuadrada
                        Box(
                            modifier = Modifier
                                .size(200.dp)  // Ventana cuadrada
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),  // Fondo del diálogo transparente
                                    shape = RoundedCornerShape(16.dp)  // Bordes redondeados
                                )
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dialogMessage,
                                color = Color.White,  // Texto blanco
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteProductItem(product: Product, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate("productDetail/${product.id}") },
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

        // Nombre del producto y precio
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.5f)  // Limitar el nombre a la mitad del ancho
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.width(18.dp))

        // Precio en negrita
        Text(
            text = "$${"%.2f".format(product.price)}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        // Flecha para navegar al detalle del producto
        IconButton(onClick = {
            navController.navigate("productDetail/${product.id}")
        }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Details")
        }
    }

    // Divider entre productos
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.primary
    )
}

