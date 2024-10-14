// Dentro de model/AuthModels.kt
package com.example.nectartorres.data.api

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)