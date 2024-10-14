package com.example.nectartorres.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nectartorres.ui.components.ProductCard
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu
import com.example.nectartorres.viewmodels.AuthViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.viewmodels.ProductViewModel



import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.nectartorres.viewmodels.UiState


@Composable
fun SearchResultsScreen(
    navController: NavController,
    query: String,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel
) {
    var searchQuery by rememberSaveable { mutableStateOf(query) }
    val productState by productViewModel.products.collectAsState()

    // Realiza la búsqueda cuando se carga la pantalla o al cambiar el query
    LaunchedEffect(searchQuery) {
        productViewModel.loadProducts()  // Llamamos a la función para cargar productos sin callback
    }

    Scaffold(
        topBar = {
            TopAppBarWithMenu(title = "Search", onMenuClick = { /* Acción del menú */ })
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Campo de búsqueda con manejo del teclado
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search Store", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFF2F3F2), RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    errorTextColor = Color.Red,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    errorCursorColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotBlank()) {
                            navController.navigate("search/$searchQuery")
                        }
                    }
                )
            )

            // Mostrar los productos filtrados según el estado de la UI
            when (productState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    val searchResults = (productState as UiState.Success<List<Product>>).data.filter {
                        it.title.contains(searchQuery, ignoreCase = true)
                    }

                    if (searchResults.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No results found for \"$searchQuery\"", style = MaterialTheme.typography.bodyMedium)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(searchResults.chunked(2)) { rowProducts ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    rowProducts.forEach { product ->
                                        ProductCard(
                                            product = product,
                                            navController = navController,
                                            productViewModel = productViewModel,
                                            authViewModel = authViewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error loading products: ${(productState as UiState.Error).message}")
                    }
                }
            }
        }
    }
}
