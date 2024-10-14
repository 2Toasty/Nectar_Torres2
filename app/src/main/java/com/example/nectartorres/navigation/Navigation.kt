package com.example.nectartorres.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nectartorres.screens.*
import com.example.nectartorres.screens.CartScreen
import com.example.nectartorres.screens.FullScreenFailureDialog
import com.example.nectartorres.screens.FavoritesScreen
import com.example.nectartorres.screens.HomeScreen
import com.example.nectartorres.screens.LocationSelectionScreen
import com.example.nectartorres.screens.OnboardingScreen
import com.example.nectartorres.screens.SearchResultsScreen
import com.example.nectartorres.screens.SignUpScreen
import com.example.nectartorres.screens.ProductDetailScreen
import com.example.nectartorres.screens.ProfileScreen
import com.example.nectartorres.screens.FullScreenSuccessDialog
import com.example.nectartorres.screens.CategoryProductsScreen  // Importación de la pantalla de productos por categoría
import com.example.nectartorres.viewmodels.AuthViewModel
import com.example.nectartorres.viewmodels.ProductViewModel
import com.example.nectartorres.screens.FindProductsScreen

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.opcional.DarkModeTransitionScreen
import com.example.nectartorres.viewmodels.UiState


@Composable
fun Navigation(navController: NavHostController, productViewModel: ProductViewModel, authViewModel: AuthViewModel) {

    NavHost(navController = navController, startDestination = "splash") {
        // Pantalla de splash
        composable("splash") {
            SplashScreen(navController = navController)
        }
        // Pantalla de onboarding
        composable("onboarding") {
            OnboardingScreen(navController = navController)
        }
        // Pantalla de login
        composable("login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        // Pantalla de registro
        composable("signup") {
            SignUpScreen(navController = navController, viewModel = authViewModel)
        }
        // Pantalla de selección de ubicación
        composable("location") {
            LocationSelectionScreen(navController = navController, authViewModel = authViewModel, productViewModel = productViewModel)
        }
        // Pantalla principal
        composable("home") {
            HomeScreen(navController = navController, productViewModel = productViewModel, authViewModel = authViewModel)
        }
        // Pantalla de favoritos
        composable("favorites") {
            FavoritesScreen(navController = navController, authViewModel = authViewModel)
        }
        // Pantalla de carrito
        composable("cart") {
            CartScreen(navController = navController, authViewModel = authViewModel)
        }
        // Pantalla de detalle de producto
        composable("productDetail/{productId}") { backStackEntry ->
            // Obtener el productId de los argumentos de la navegación
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()

            // Usar collectAsState para observar el estado de los productos
            val productsState by productViewModel.products.collectAsState()

            // Buscar el producto en el estado de los productos
            val product = when (productsState) {
                is UiState.Success -> (productsState as UiState.Success<List<Product>>).data.find { it.id == productId }
                else -> null
            }

            // Mostrar la pantalla de detalle del producto y pasar ambos ViewModels
            ProductDetailScreen(
                navController = navController,
                product = product,
                authViewModel = authViewModel
            )
        }

        // Pantalla de productos por categoría
        composable("category/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            CategoryProductsScreen(
                navController = navController,
                category = category,
                productViewModel = productViewModel,
                authViewModel = authViewModel // Pasamos AuthViewModel aquí
            )
        }

        // Pantalla de perfil
        composable("profile") {
            ProfileScreen(navController = navController)
        }

        composable("checkout/{totalAmount}") { backStackEntry ->
            val totalAmount = backStackEntry.arguments?.getString("totalAmount")?.toDoubleOrNull() ?: 0.0
            CheckoutScreen(navController = navController, totalAmount = totalAmount)
        }

//        // Pantalla de checkout
//        composable("checkout") {
//            CheckoutScreen(navController = navController, totalAmount = 0.0) // Ajusta el total según tu lógica
//        }


        // Pantalla de éxito en el pedido
        composable("success") {
            FullScreenSuccessDialog(navController = navController)
        }
        // Pantalla de fallo en el pedido
        composable("failure") {
            FullScreenFailureDialog(navController = navController)
        }
        // Pantalla de búsqueda de productos
        composable("search/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SearchResultsScreen(navController = navController, query = query, productViewModel = productViewModel, authViewModel = authViewModel)
        }

        composable("explore") {
            FindProductsScreen(navController = navController, productViewModel = productViewModel)
        }

//        composable("dark_mode_transition") {
//            DarkModeTransitionScreen(navController) {
//                // Al terminar el video, volver a la pantalla de perfil
//                navController.navigate("profile") {
//                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                }
//            }
//        }

    }
}
