package com.example.disasterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Define custom colors
val BackgroundColor = Color(0xFFF2E3D5)
val TextLabelColor = Color(0xFF001542)
val FieldBackgroundColor = Color.White
val HeaderColor = Color(0xFF011F26)
val CancelButtonColor = Color(0xFFA62B1F)
val SaveButtonColor = Color(0xFF2E5902)

@Composable
fun HelpFormScreen( navController: NavController ) {
    val scrollState = rememberScrollState()

    val name = remember { mutableStateOf(TextFieldValue("")) }
    val contactInfo = remember { mutableStateOf(TextFieldValue("")) }
    val address = remember { mutableStateOf(TextFieldValue("")) }
    val maxCapacity = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(32.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Add padding above the title for better alignment

        // Add top title
        Text(
            text = "Yardım Bilgileri",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = HeaderColor,
            modifier = Modifier.padding(bottom = 32.dp) // Padding below the title
        )

        FormField(
            value = name.value,
            onValueChange = { name.value = it },
            label = "İsim",
            minHeight = 48.dp
        )

        Spacer(modifier = Modifier.height(24.dp)) // Increase space between fields

        FormField(
            value = contactInfo.value,
            onValueChange = { contactInfo.value = it },
            label = "İletişim Bilgisi",
            minHeight = 48.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormField(
            value = address.value,
            onValueChange = { address.value = it },
            label = "Adres Açıklaması",
            minHeight = 48.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormField(
            value = maxCapacity.value,
            onValueChange = { maxCapacity.value = it },
            label = "Kapasite",
            minHeight = 48.dp,
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(32.dp)) // Extra space before the buttons

        // Buttons in a Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screen.MainScreen.route) },
                colors = ButtonDefaults.buttonColors(containerColor = CancelButtonColor),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp) // Increase button height
            ) {
                Text(text = "İptal Et", fontSize = 16.sp)
            }

            Button(
                onClick = { /* Handle save action */ },
                colors = ButtonDefaults.buttonColors(containerColor = SaveButtonColor),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp) // Increase button height
            ) {
                Text(text = "Kaydet", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun FormField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    minHeight: Dp,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            color = TextLabelColor,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp) // Padding between label and text field
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .background(FieldBackgroundColor)
                .padding(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = TextLabelColor),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Text(text = label, color = Color.Gray)
                }
                innerTextField()
            }
        )
    }
}
