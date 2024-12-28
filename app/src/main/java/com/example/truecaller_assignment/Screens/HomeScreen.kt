package com.example.truecaller_assignment.Screens

import HomeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel,
               onNavigateToProfile: () -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
                color = Color.White
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Welcome!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Manual Registration Button
                Button(
                    onClick = {
                        navController.navigate("/manual_registration")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Manual Registration",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                // 1-click Registration Button
                Button(
                    onClick = {
                        viewModel.initiateRegistration(context as FragmentActivity)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "1-click Registration",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                when (uiState) {
                    is RegistrationState.InProgress -> {
                        CircularProgressIndicator()
                    }
                    is RegistrationState.Error -> {
                        Text(
                            text = (uiState as RegistrationState.Error).message,
                            color = Color.Red
                        )
                    }
                    is RegistrationState.Success -> {
                        LaunchedEffect(Unit) {
                            onNavigateToProfile()
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
