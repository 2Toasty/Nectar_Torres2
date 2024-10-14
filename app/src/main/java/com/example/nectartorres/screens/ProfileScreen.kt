package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nectartorres.ui.components.BottomNavigationBar
import com.example.nectartorres.ui.components.TopAppBarWithMenu


import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector

import com.example.nectartorres.R


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.res.painterResource


data class ProfileOption(val text: String, val icon: ImageVector)


//original y funcional
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                title = "Account",
                onMenuClick = { /* Lógica del menú lateral */ }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre elementos
        ) {
            // Parte superior: Avatar y detalles
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen de perfil (ficticia)
                    Image(
                        painter = painterResource(id = R.drawable.toast_avatar), // Usa una imagen inventada
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )


                    Spacer(modifier = Modifier.width(16.dp))


                    // Nombre del usuario y correo
                    Column {
                        Text(
                            text = "Toasty",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "mantequita@gmail.com",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }


                    // Icono para editar perfil
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /* Lógica para editar perfil */ }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
                    }
                }
            }


            // Opciones del perfil
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }


            items(listOf(
                ProfileOption("Orders", Icons.Default.ShoppingBag),
                ProfileOption("My Details", Icons.Default.Person),
                ProfileOption("Delivery Address", Icons.Default.LocationOn),
                ProfileOption("Payment Methods", Icons.Default.CreditCard),
                ProfileOption("Promo Cord", Icons.Default.LocalOffer),
                ProfileOption("Notifications", Icons.Default.Notifications),
                ProfileOption("Help", Icons.AutoMirrored.Filled.Help)
            )) { option ->
                ProfileOptionItem(icon = option.icon, text = option.text) { /* Acción */ }
            }


            item {
                // Dark mode toggle
                DarkModeToggle()
            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                // Botón de "Log Out"
                Button(
                    onClick = {
                        // Redirigir a la pantalla de login
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(48.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Log Out")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Log Out")
                }
            }
        }
    }
}


//@Composable
//fun ProfileOptionItem(text: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = text, style = MaterialTheme.typography.bodyMedium)
//    }
//}

@Composable
fun ProfileOptionItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Go to $text")
    }
    HorizontalDivider()
}



//originalfuncional
@Composable
fun DarkModeToggle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Mode", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))


        // Switch para modo oscuro
        var isDarkMode by remember { mutableStateOf(false) }
        Switch(
            checked = isDarkMode,
            onCheckedChange = { isDarkMode = it }
        )
    }
}

//@Composable
//fun ProfileScreen(navController: NavController) {
//    var isDarkMode by remember { mutableStateOf(false) } // Estado para el modo oscuro
//
//    Scaffold(
//        topBar = {
//            TopAppBarWithMenu(
//                title = "Account",
//                onMenuClick = { /* Lógica del menú lateral */ }
//            )
//        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre elementos
//        ) {
//            // Parte superior: Avatar y detalles
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    // Imagen de perfil (ficticia)
//                    Image(
//                        painter = painterResource(id = R.drawable.toast_avatar), // Usa una imagen inventada
//                        contentDescription = "Profile Image",
//                        modifier = Modifier
//                            .size(64.dp)
//                            .clip(CircleShape)
//                    )
//
//
//                    Spacer(modifier = Modifier.width(16.dp))
//
//
//                    // Nombre del usuario y correo
//                    Column {
//                        Text(
//                            text = "Toasty",
//                            style = MaterialTheme.typography.titleMedium,
//                            modifier = Modifier.padding(bottom = 4.dp)
//                        )
//                        Text(
//                            text = "mantequita@gmail.com",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//
//
//                    // Icono para editar perfil
//                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(onClick = { /* Lógica para editar perfil */ }) {
//                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
//                    }
//                }
//            }
//
//            // Opciones del perfil
//            item {
//                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//            }
//
//            items(listOf(
//                ProfileOption("Orders", Icons.Default.ShoppingBag),
//                ProfileOption("My Details", Icons.Default.Person),
//                ProfileOption("Delivery Address", Icons.Default.LocationOn),
//                ProfileOption("Payment Methods", Icons.Default.CreditCard),
//                ProfileOption("Promo Cord", Icons.Default.LocalOffer),
//                ProfileOption("Notifications", Icons.Default.Notifications),
//                ProfileOption("Help", Icons.AutoMirrored.Filled.Help)
//            )) { option ->
//                ProfileOptionItem(icon = option.icon, text = option.text) { /* Acción */ }
//            }
//
//            item {
//                // Pasar el estado del modo oscuro y la función para alternar
//                DarkModeToggle(isDarkMode = isDarkMode, onToggleDarkMode = { isDarkMode = it }, navController = navController)
//            }
//
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            item {
//                // Botón de "Log Out"
//                Button(
//                    onClick = {
//                        // Redirigir a la pantalla de login
//                        navController.navigate("login") {
//                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                        .height(48.dp),
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Log Out")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "Log Out")
//                }
//            }
//        }
//    }
//}

//@Composable
//fun DarkModeToggle(
//    isDarkMode: Boolean,
//    onToggleDarkMode: (Boolean) -> Unit,
//    navController: NavController // Pasar NavController para la navegación
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "Dark Mode", style = MaterialTheme.typography.bodyMedium)
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Switch para modo oscuro
//        Switch(
//            checked = isDarkMode,
//            onCheckedChange = { isChecked ->
//                // Primero activamos la transición de video
//                navController.navigate("dark_mode_transition")
//                // Cambiamos el estado del modo oscuro
//                onToggleDarkMode(isChecked)
//            }
//        )
//    }
//}