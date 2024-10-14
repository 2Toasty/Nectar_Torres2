package com.example.nectartorres.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.Alignment

import androidx.compose.material3.MaterialTheme
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.ui.components.ProductCard
import com.example.nectartorres.viewmodels.AuthViewModel
import com.example.nectartorres.viewmodels.ProductViewModel
import com.example.nectartorres.viewmodels.UiState

@Composable
fun HomeScreen(navController: NavController, productViewModel: ProductViewModel,authViewModel:AuthViewModel) {
    val productState = productViewModel.products.collectAsState().value

    Scaffold(
        topBar = { TopAppBarWithMenu(title = "Shop", onMenuClick = { /* Lógica del menú */ }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        when (productState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${(productState as UiState.Error).message}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            is UiState.Success -> {
                val products = (productState as UiState.Success<List<Product>>).data
                if (products.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No products available", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    // Mostrar los productos
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(16.dp)
                    ){
                // Sección "Exclusive Offer"
                item {
                    Text(
                        text = "Exclusive Offer",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(products.take(5)) { product ->
                            ProductCard(
                                product = product,
                                navController = navController,
                                authViewModel = authViewModel,  // Asegúrate de pasar authViewModel
                                productViewModel = productViewModel  // Asegúrate de pasar productViewModel
                            )
                        }
                    }

                }

                // Espaciador entre secciones
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Best Selling",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(products.drop(5)) { product ->
                            ProductCard(
                                product = product,
                                navController = navController,
                                authViewModel = authViewModel,
                                productViewModel = productViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}}}