package com.example.nectartorres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nectartorres.data.api.FakeStoreApi
import com.example.nectartorres.data.repository.FirebaseRepository
import com.example.nectartorres.data.repository.ProductRepository
import com.example.nectartorres.navigation.Navigation
import com.example.nectartorres.ui.theme.NectarTorresTheme
import com.example.nectartorres.viewmodels.AuthViewModel
import com.example.nectartorres.viewmodels.ProductViewModel
import com.example.nectartorres.viewmodels.ProductViewModelFactory
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.nectartorres.data.api.RetrofitInstance
import com.example.nectartorres.viewmodels.AuthViewModelFactory


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de Firebase
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Inicialización de los repositorios
        val firebaseRepository = FirebaseRepository(firestore)
        val productRepository = ProductRepository(RetrofitInstance.api)

        setContent {
            NectarTorresTheme {
                val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(firebaseRepository,productRepository))
                val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(productRepository))

                // Configurar navegación, pasando los ViewModels
                Navigation(
                    navController = rememberNavController(),
                    productViewModel = productViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}


