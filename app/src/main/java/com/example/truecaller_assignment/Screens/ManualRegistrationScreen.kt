package com.example.truecaller_assignment.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ManualRegistrationScreen(navController: NavController) {
    val context = LocalContext.current

    // State variables for the input fields
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var mobileNumber by remember { mutableStateOf(TextFieldValue("")) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        ,
        color = Color.White


    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
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
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("First Name") },
                keyboardOptions = KeyboardOptions.Default,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black, // Text color when typing
                    unfocusedTextColor = Color.Gray, // Text color when not focused
                    disabledContainerColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black, // Border color when not focused
                )
            )
            // Last Name Field
            Text(text = "Last Name", fontSize = 16.sp, color = Color.Black)
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Last Name") },
                keyboardOptions = KeyboardOptions.Default,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black, // Text color when typing
                    unfocusedTextColor = Color.Gray, // Text color when not focused
                    disabledContainerColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black, // Border color when not focused
                )
            )

            // Mobile Number Field
            Text(text = "Mobile Number", fontSize = 16.sp, color = Color.Black)
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Mobile Number") },
                keyboardOptions = KeyboardOptions.Default,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black, // Text color when typing
                    unfocusedTextColor = Color.Gray, // Text color when not focused
                    disabledContainerColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black, // Border color when not focused
                )
            )


            // Submit Button
            Button(
                onClick = {
                    val nameRegex = Regex("^[a-zA-Z]+\$")
                    when {
                        firstName.text.isEmpty() -> {
                            Toast.makeText(context, "First Name cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        lastName.text.isEmpty() -> {
                            Toast.makeText(context, "Last Name cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        !nameRegex.matches(firstName.text) -> {
                            Toast.makeText(context, "First Name cannot contain numbers or special characters", Toast.LENGTH_SHORT).show()
                        }
                        !nameRegex.matches(lastName.text) -> {
                            Toast.makeText(context, "Last Name cannot contain numbers or special characters", Toast.LENGTH_SHORT).show()
                        }
                        mobileNumber.text.isEmpty() -> {
                            Toast.makeText(context, "Mobile Number cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        mobileNumber.text.length != 10 || !mobileNumber.text.all { it.isDigit() } -> {
                            Toast.makeText(context, "Mobile Number must be exactly 10 digits", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Navigate to InfoDisplayScreen and pass the data
                            navController.navigate("infoDisplayScreen/${firstName.text}/${lastName.text}/${mobileNumber.text}")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Submit", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
