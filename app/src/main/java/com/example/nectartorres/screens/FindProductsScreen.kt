package com.example.nectartorres.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu
import com.example.nectartorres.viewmodels.ProductViewModel



import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.nectartorres.viewmodels.UiState
import kotlin.math.absoluteValue

@Composable
fun FindProductsScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val categoriesState = productViewModel.categories.collectAsState().value

    // Cargar categorías cuando se monta la pantalla
    LaunchedEffect(Unit) {
        productViewModel.loadCategories()
    }

    Scaffold(
        topBar = { TopAppBarWithMenu(title = "Find Products", onMenuClick = { /* Menú */ }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // TextBox con íconos lupa y filtro, replicando la visual del monolito
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search Store", color = Color.LightGray) },  // Placeholder similar
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },  // Icono de lupa
                trailingIcon = {  // Icono de filtro a la derecha
                    Icon(
                        painter = painterResource(id = R.drawable.filter),  // Icono desde drawable
                        contentDescription = "Filter Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFF2F3F2), RoundedCornerShape(8.dp)),  // Fondo claro y bordes redondeados
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),  // Opción de teclado
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotBlank()) {
                            // Ejecutar la acción de búsqueda
                            navController.navigate("search/$searchQuery")
                        }
                    }
                )
            )

            // Manejar el estado de las categorías
            when (categoriesState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    val categories = (categoriesState as UiState.Success<List<String>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories.chunked(2)) { rowCategories ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowCategories.forEach { category ->
                                    CategoryCard(category = category, navController = navController)
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error loading categories: ${(categoriesState as UiState.Error).message}")
                    }
                }
            }
        }
    }
}




@Composable
fun CategoryCard(category: String, navController: NavController) {
    // Lista de colores de fondo para las tarjetas
    val cardColors = listOf(
        Color(0xFFBBDEFB),  // Azul claro
        Color(0xFFC8E6C9),  // Verde claro
        Color(0xFFFFF9C4),  // Amarillo claro
        Color(0xFFFFCCBC),  // Naranja claro
        Color(0xFFD7CCC8)   // Marrón claro
    )

    // Selecciona un color diferente para cada tarjeta basado en el hash de la categoría
    val backgroundColor = cardColors[category.hashCode().absoluteValue % cardColors.size]
    val borderColor = backgroundColor.darken()  // Oscurecemos el borde
    val borderThickness = 2.dp  // Aumentamos el grosor del borde

    Card(
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp)
            .clickable { navController.navigate("category/$category") },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(borderThickness, borderColor),  // Usamos un borde más grueso y oscuro
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,  // Centra verticalmente
            modifier = Modifier
                .fillMaxSize()  // Para que ocupe todo el espacio de la Card
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = getCategoryImage(category)),
                contentDescription = category,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },  // Primera letra en mayúscula
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,  // Centrar el texto en varias líneas
                maxLines = 2,  // Limitar a 2 líneas
                overflow = TextOverflow.Ellipsis  // Si el texto es muy largo, se muestra "..."
            )
        }
    }
}

// Función de extensión para oscurecer un color
fun Color.darken(factor: Float = 0.7f): Color {
    return Color(red = red * factor, green = green * factor, blue = blue * factor, alpha = alpha)
}






fun getCategoryImage(category: String): Int {
    return when (category) {
        "men's clothing" -> R.drawable.men_clothing
        "women's clothing" -> R.drawable.women_clothing
        "electronics" -> R.drawable.electronics
        "jewelery" -> R.drawable.jewelery
        else -> R.drawable.ic_launcher_foreground
    }
}
