package com.example.nectartorres.data.api

import com.example.nectartorres.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// API para manejar la autenticación y cargar productos
interface FakeStoreApi {

    // Login
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    // Obtener todos los productos
    @GET("products")
    suspend fun getProducts(): List<Product>

    // Obtener todos los carritos
    @GET("carts")
    suspend fun getCarts(): List<Cart>

    // Obtener todas las categorías de productos
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    // Obtener productos de una categoría específica
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>
}