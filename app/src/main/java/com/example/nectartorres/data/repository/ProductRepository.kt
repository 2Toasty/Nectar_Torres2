package com.example.nectartorres.data.repository

import com.example.nectartorres.data.api.FakeStoreApi
import com.example.nectartorres.data.model.Cart
import com.example.nectartorres.data.model.Product

class ProductRepository(
    private val api: FakeStoreApi
) {

    // Obtener productos desde la API
    suspend fun getProducts(): List<Product> {
        return try {
            api.getProducts()
        } catch (e: Exception) {
            emptyList() // En caso de error, retornamos una lista vacía
        }
    }

    // Obtener categorías de productos
    suspend fun getCategories(): List<String> {
        return try {
            api.getCategories()
        } catch (e: Exception) {
            emptyList() // En caso de error, retornamos una lista vacía
        }
    }

    // Obtener productos de una categoría específica
    suspend fun getProductsByCategory(category: String): List<Product> {
        return try {
            api.getProductsByCategory(category)
        } catch (e: Exception) {
            emptyList() // En caso de error, retornamos una lista vacía
        }
    }

    // Obtener todos los carritos
    suspend fun getCarts(): List<Cart> {
        return try {
            api.getCarts()
        } catch (e: Exception) {
            emptyList() // En caso de error, retornamos una lista vacía
        }
    }
}
