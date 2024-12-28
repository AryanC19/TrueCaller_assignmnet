package com.example.truecaller_assignment.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoDisplayScreen(
    navController: NavController,
    firstName: String,
    lastName: String,
    mobileNumber: String
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        color = Color.White
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack()  }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .padding(top = 68.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // First Name Field
            Text( text = "First Name", fontSize = 16.sp, color = Color.Black)
            // Display First Name
            Text(
                text = firstName,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            // Last Name Field
            Text(text = "Last Name", fontSize = 16.sp, color = Color.Black)
            // Display Last Name
            Text(
                text = lastName,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            // Mobile Number Field
            Text(text = "Mobile Number", fontSize = 16.sp, color = Color.Black)
            // Display Mobile Number
            Text(
                text = mobileNumber,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    // Perform your success action here (e.g., navigate back or show a success message)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors( Color.Green)
            ) {
                Text(
                    text = "Success !",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewInfoDisplayScreen() {
    // Provide sample data for preview
    InfoDisplayScreen(
        firstName = "John", lastName = "Doe", mobileNumber = "1234567890",
        navController = TODO()
    )
}
