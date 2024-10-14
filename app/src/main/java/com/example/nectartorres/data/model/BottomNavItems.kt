package com.example.nectartorres.data.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItems(
    val route: String,
    val icon: ImageVector? = null,
    val painterIcon: Painter? = null, // Añadimos una propiedad para íconos del tipo Painter
    val label: String
)

