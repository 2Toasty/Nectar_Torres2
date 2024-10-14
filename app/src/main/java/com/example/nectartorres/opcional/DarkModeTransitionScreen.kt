package com.example.nectartorres.opcional

import android.content.ContentResolver
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.nectartorres.R

@OptIn(UnstableApi::class)
@Composable
fun DarkModeTransitionScreen(
    navController: NavController, // Para volver al ProfileScreen al terminar el video
    onVideoEnd: () -> Unit // Callback para volver a la pantalla anterior cuando termine el video
) {
    var isBlackScreenVisible by remember { mutableStateOf(true) } // Para el fade-in inicial
    val context = LocalContext.current
    val packageName = context.packageName // Obtener el nombre del paquete

    val videoUri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(packageName)
        .path(R.raw.dark_node.toString()) // Usa el ID del recurso como la ruta
        .build()  // Construir el URI con Uri.Builder()

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .build()  // Usar MediaItem.Builder para construir el MediaItem
            setMediaItem(mediaItem)
            prepare()
        }
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000) // Tiempo para el fade in inicial
        isBlackScreenVisible = false // Hacer desaparecer la pantalla negra y mostrar el video
        exoPlayer.playWhenReady = true // Reproducir el video

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnd() // Volver a la pantalla de perfil cuando termine el video
                    // Navega de vuelta al perfil
                    navController.navigate("profile") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro detrás del video
    ) {
        // Pantalla negra con Fade-In durante 2 segundos
        AnimatedVisibility(
            visible = isBlackScreenVisible,
            enter = fadeIn(tween(2000)),
            exit = fadeOut(tween(2000))
        ) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black))
        }

        // Video que se reproduce una vez que la pantalla negra se desvanece
        if (!isBlackScreenVisible) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false // Sin controles para el usuario
                        setBackgroundColor(android.graphics.Color.BLACK) // Fondo negro para el PlayerView
                         // Ajusta el video para llenar la pantalla
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black) // Fondo negro para el contenedor del video
            )
        }
    }
}

