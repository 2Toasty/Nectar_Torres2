package com.example.nectartorres.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.ui.components.ProductCard
import com.example.nectartorres.viewmodels.ProductViewModel
import com.example.nectartorres.viewmodels.UiState
import kotlinx.coroutines.launch




import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.autoSaver
import com.example.nectartorres.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    navController: NavController,
    category: String,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel // Añadimos el AuthViewModel
) {
    // Observamos el estado de los productos por categoría en ProductViewModel
    val categoryProductsState by productViewModel.products.collectAsState()

    // Cargamos los productos de la categoría cuando la pantalla se monta
    LaunchedEffect(Unit) {
        productViewModel.loadProductsByCategory(category)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.replaceFirstChar { it.uppercaseChar() }) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (categoryProductsState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val categoryProducts = (categoryProductsState as UiState.Success<List<Product>>).data
                if (categoryProducts.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No products available", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(categoryProducts.chunked(2)) { rowProducts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowProducts.forEach { product ->
                                    ProductCard(
                                        product = product,
                                        navController = navController,
                                        authViewModel = authViewModel, // Pasamos el AuthViewModel aquí
                                        productViewModel = productViewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error loading products: ${(categoryProductsState as UiState.Error).message}")
                }
            }
        }
    }
}



