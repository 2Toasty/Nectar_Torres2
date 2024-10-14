// data/repository/FirebaseRepository.kt

package com.example.nectartorres.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository(private val firestore: FirebaseFirestore) {

    // Agregar referencia del producto a favoritos
    suspend fun addProductToFavorites(productId: String) {
        try {
            firestore.collection("favoritodos")
                .document(productId)  // El documento será el ID del producto
                .set(mapOf("productId" to productId))
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Obtener productos favoritos (solo IDs)
    suspend fun fetchFavoriteProductIds(): List<String> {
        return try {
            val favoriteCollection = firestore.collection("favoritodos")
                .get()
                .await()
            favoriteCollection.documents.mapNotNull { document ->
                document.getString("productId")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Eliminar todos los productos de favoritos
    suspend fun clearFavorites() {
        try {
            val favoriteCollection = firestore.collection("favoritodos")
                .get()
                .await()
            favoriteCollection.documents.forEach { document ->
                document.reference.delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}