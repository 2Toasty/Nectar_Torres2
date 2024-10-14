// data/model/Product.kt

package com.example.nectartorres.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)

@Serializable
data class ProductInCart(
    val productId: Int,
    val quantity: Int
)

@Serializable
data class Cart(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<ProductInCart>
)