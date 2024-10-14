package com.example.nectartorres.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.data.model.BottomNavItems // O cambiar a la ruta correcta si está en ui.navigation



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) // Agrega sombra y redondea el borde superior
    ) {
        NavigationBar(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp) // Redondea el borde superior
                ),
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            val items = listOf(
                BottomNavItems(route = "home", icon = Icons.Default.Store, label = "Shop"),
                BottomNavItems(route = "explore", icon = Icons.AutoMirrored.Filled.ManageSearch, label = "Explore"),
                BottomNavItems(route = "cart", icon = Icons.Default.ShoppingCartCheckout, label = "Cart"),
                BottomNavItems(route = "favorites", icon = Icons.Default.FavoriteBorder, label = "Favourite"),
                BottomNavItems(route = "profile", painterIcon = painterResource(id = R.drawable.user), label = "Account")
            )

            items.forEach { item ->

                val isSelected = currentRoute == item.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        item.icon?.let {
                            Icon(it, contentDescription = item.label, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                        item.painterIcon?.let {
                            Icon(painter = it, contentDescription = item.label, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                        }
                    },
                    label = {
                        Text(
                            item.label,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent  // Elimina el sombreado ovalado
                    )
                )
            }
        }
    }
}

