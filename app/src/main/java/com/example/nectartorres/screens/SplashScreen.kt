package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.nectartorres.R


@Composable
fun SplashScreen(navController: NavController) {
    // Fondo verde
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),  // Fondo verde
        contentAlignment = Alignment.Center
    ) {
        // Imagen del logo blanco
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_white),  // Logo blanco
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)  // Tamaño del logo
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nectar",  // Cambia a tu nombre o logo de texto
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    // Temporizador de 2 segundos para navegar al onboarding
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun SplashScreenPreview() {
//    SplashScreen(navController = rememberNavController())
//}
//
//@Composable
//fun SplashScreenPreviewContent() {
//    // Fondo verde
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.primary),  // Fondo verde
//        contentAlignment = Alignment.Center
//    ) {
//        // Imagen del logo blanco
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Image(
//                painter = painterResource(id = R.drawable.logo_white),  // Logo blanco
//                contentDescription = "Logo",
//                modifier = Modifier.size(150.dp)  // Tamaño del logo
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Nectar",  // Cambia a tu nombre o logo de texto
//                style = MaterialTheme.typography.displayMedium,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//        }
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//fun SplashScreenPreview() {
//    NectarTorresTheme {  // Agrega el tema alrededor del contenido
//        SplashScreenPreviewContent()
//    }
//}
