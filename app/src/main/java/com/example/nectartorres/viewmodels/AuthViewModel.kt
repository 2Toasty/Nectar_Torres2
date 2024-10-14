package com.example.nectartorres.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nectartorres.data.api.FakeStoreApi
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.nectartorres.data.api.AuthRequest
import com.example.nectartorres.data.api.RetrofitInstance
import com.example.nectartorres.data.repository.ProductRepository


class AuthViewModel(private val firebaseRepository: FirebaseRepository,
private val productRepository: ProductRepository  // Inyección de ProductRepository
) : ViewModel() {
    var token by mutableStateOf<String?>(null)
    var errorMessage by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)
    var products by mutableStateOf<List<Product>>(emptyList())
    var favorites by mutableStateOf<List<Product>>(emptyList())
    var cartItems by mutableStateOf<List<Product>>(emptyList())
    var categories by mutableStateOf<List<String>>(emptyList())

    fun performLogin(username: String, password: String, onLoginSuccess: () -> Unit) {
        isLoading = true
        errorMessage = null
        token = null

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(AuthRequest(username, password))
                token = response.token
                onLoginSuccess()
            } catch (e: Exception) {
                errorMessage = "Error en el login: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

//    fun loadProducts(onProductsLoaded: () -> Unit) {
//        isLoading = true
//        errorMessage = null
//
//        viewModelScope.launch {
//            try {
//                val productResponse = FakeStoreApi.getProducts()
//                products = productResponse
//                onProductsLoaded()
//            } catch (e: Exception) {
//                errorMessage = "Error al cargar productos: ${e.message}"
//            } finally {
//                isLoading = false
//            }
//        }
//    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favoriteIds = firebaseRepository.fetchFavoriteProductIds()
            val products = productRepository.getProducts()
            favorites = favoriteIds.mapNotNull { productId ->
                products.find { it.id.toString() == productId }
            }
        }
    }

    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            firebaseRepository.addProductToFavorites(product.id.toString())
            loadFavorites()
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            firebaseRepository.clearFavorites()
            favorites = emptyList()
        }
    }

    fun loadRandomCart(onCartLoaded: () -> Unit) {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                // Cargar los productos directamente desde el repositorio
                val products = productRepository.getProducts()

                // Obtener el carrito aleatorio desde la API
                val randomCart = RetrofitInstance.api.getCarts().random()

                // Extraer los productos del carrito aleatorio
                val cartProducts = randomCart.products.mapNotNull { cartItem ->
                    products.find { it.id == cartItem.productId }
                }

                cartItems = cartProducts
                onCartLoaded()

            } catch (e: Exception) {
                errorMessage = "Error al cargar el carrito: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }


//    fun loadCategories() {
//        isLoading = true
//        errorMessage = null
//
//        viewModelScope.launch {
//            try {
//                categories = FakeStoreApi.getCategories()
//            } catch (e: Exception) {
//                errorMessage = "Error al cargar las categorías: ${e.message}"
//            } finally {
//                isLoading = false
//            }
//        }
//    }

//    fun loadProductsByCategory(category: String, onProductsLoaded: () -> Unit) {
//        isLoading = true
//        errorMessage = null
//
//        viewModelScope.launch {
//            try {
//                categoryProducts = FakeStoreApi.getProductsByCategory(category)
//                onProductsLoaded()
//            } catch (e: Exception) {
//                errorMessage = "Error al cargar productos de la categoría: ${e.message}"
//            } finally {
//                isLoading = false
//            }
//        }
//    }

//    var categoryProducts by mutableStateOf<List<Product>>(emptyList())
}