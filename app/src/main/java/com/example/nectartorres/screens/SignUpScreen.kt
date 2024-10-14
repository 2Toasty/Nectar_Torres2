package com.example.nectartorres.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nectartorres.R
import com.example.nectartorres.viewmodels.AuthViewModel


import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.nectartorres.ui.components.DialogTip


@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var signUpError by remember { mutableStateOf("") }


    // Estado para mostrar/ocultar el ToolTip
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo color en el Sign Up
        Image(
            painter = painterResource(id = R.drawable.logo_color),
            contentDescription = "Logo",
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Sign Up",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
            modifier = Modifier.align(Alignment.Start)

            )

        Text(
            text = "Enter your credential to continue",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Username",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth(),
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


        Text(
            text = "Email",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth(),
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


        Text(
            text = "Password",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(),
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

        Text(
            text = "By continuing you agree to our Terms of Service and Privacy Policy.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.Start),
            color = Color.Gray
        )





        Spacer(modifier = Modifier.height(16.dp))

        if (signUpError.isNotEmpty()) {
            Text(
                text = signUpError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón verde rectangular con puntas redondeadas
        Button(
            onClick = {

                showDialog = true  // Activar el tooltip cuando se hace clic en la X

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium // Puntas redondeadas
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "Sign Up", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        // Llamar al Dialog para mostrar el mensaje
        DialogTip(showDialog = showDialog, onDismiss = { showDialog = false })

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "Already have an account? Login")
        }
    }
}