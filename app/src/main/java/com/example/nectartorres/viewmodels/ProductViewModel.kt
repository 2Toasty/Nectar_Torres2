package com.example.nectartorres.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nectartorres.data.model.Product
import com.example.nectartorres.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



// Definimos los posibles estados de UI
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ProductViewModel(
    private val productRepository: ProductRepository) : ViewModel() {

    // Estado para manejar los productos
    private val _products = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val products: StateFlow<UiState<List<Product>>> = _products

    // Estado para manejar las categorías
    private val _categories = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<String>>> = _categories

    // Función para cargar productos desde la API
    fun loadProducts() {
        viewModelScope.launch {
            _products.value = UiState.Loading
            try {
                val products = productRepository.getProducts()
                _products.value = UiState.Success(products)
            } catch (e: Exception) {
                _products.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Función para cargar las categorías
    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = UiState.Loading
            try {
                val categories = productRepository.getCategories()
                _categories.value = UiState.Success(categories)
            } catch (e: Exception) {
                _categories.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Función para cargar productos por categoría
    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            _products.value = UiState.Loading
            try {
                val products = productRepository.getProductsByCategory(category)
                _products.value = UiState.Success(products)
            } catch (e: Exception) {
                _products.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}