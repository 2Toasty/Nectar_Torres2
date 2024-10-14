package com.example.nectartorres.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.viewmodels.AuthViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo_color),
            contentDescription = "Logo",
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Texto Sign In alineado a la izquierda
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
            modifier = Modifier.align(Alignment.Start),
            color = Color.Black
        )

        Text(
            text = "Enter your emails and password",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto User y TextField
        Text(
            text = "User",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )
        


        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Asegura el mismo tamaño de altura
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto Password y TextField
        Text(
            text = "Password",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { /* Acción para olvidar contraseña */ }) {
                Text(text = "Forgot Password?", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Login
        Button(
            onClick = {
                viewModel.performLogin(username, password) {
                    navController.navigate("location") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Log In", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link para registrarse
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don’t have an account? ",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )

            TextButton(onClick = { navController.navigate("signup") },
                       contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Signup", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


