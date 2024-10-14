package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.viewmodels.AuthViewModel
import com.example.nectartorres.viewmodels.ProductViewModel



import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavController, product: Product?, authViewModel: AuthViewModel) {
    var quantity by remember { mutableStateOf(1) }
    var showAddedToCartMessage by remember { mutableStateOf(false) }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando producto...", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Product Detail", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    item {
                        // Imagen del producto
                        Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth().heightIn(200.dp, 300.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(model = product.image),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Título del producto e ícono de favoritos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                )
                                // Estrellas y reviews
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) { index ->
                                        val filled = index < product.rating.rate.toInt()
                                        Icon(
                                            imageVector = if (filled) Icons.Default.Star else Icons.Default.StarBorder,
                                            contentDescription = null,
                                            tint = if (filled) MaterialTheme.colorScheme.primary else Color.Gray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "(${product.rating.count} reviews)",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                            // Usar AuthViewModel para agregar a favoritos
                            IconButton(onClick = {
                                authViewModel.addToFavorites(product)
                            }) {
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to Favorites")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cantidad y precio
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                Box(
                                    modifier = Modifier.size(48.dp).border(1.dp, MaterialTheme.colorScheme.onPrimary, MaterialTheme.shapes.medium),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "$quantity", style = MaterialTheme.typography.bodyLarge)
                                }
                                IconButton(onClick = { quantity++ }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Text(
                                text = "$${product.price * quantity}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Divider de color secundario
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )

                        // Sección expandible del detalle del producto
                        ExpandableSection(title = "Product Detail") {
                            Text(text = product.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }

                        // Divider de color secundario
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )

                        // Botón de añadir al carrito
                        Button(
                            onClick = {

                                showAddedToCartMessage = true
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Add to Cart",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                            )
                        }

                        // Mensaje de confirmación
                        if (showAddedToCartMessage) {
                            Text(text = "Producto agregado con éxito al carrito ;)", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = if (isExpanded) "Collapse" else "Expand"
            )
        }

        if (isExpanded) {
            content()
        }
    }
}

