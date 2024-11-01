package com.example.disasterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(isDropdownExpanded: MutableState<Boolean>, contentPadding: PaddingValues) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isDropdownExpanded.value = !isDropdownExpanded.value },
                containerColor = Color.Cyan
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            AddLocation(isDropdownExpanded, paddingValues)
        }
    )
}

@Composable
fun AddLocation(isDropdownExpanded: MutableState<Boolean>, contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        if (isDropdownExpanded.value) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 112.dp, end = 16.dp) // Adjusted padding to avoid FAB overlap
                    .background(Color.Transparent)
            ) {
                CustomDropdownItem(
                    text = "Gıda",
                    buttonColor = Color(0xFF00B3AD),
                    icon = Icons.Filled.Check,
                    onClick = { isDropdownExpanded.value = false }
                )
                CustomDropdownItem(
                    text = "Isınma",
                    buttonColor = Color(0xFF006663),
                    icon = Icons.Filled.Home,
                    onClick = { isDropdownExpanded.value = false }
                )
                CustomDropdownItem(
                    text = "İnternet",
                    buttonColor = Color(0xFFFF6B1A),
                    icon = Icons.Filled.Settings,
                    onClick = { isDropdownExpanded.value = false }
                )
            }
        }
    }
}

@Composable
fun CustomDropdownItem(
    text: String,
    buttonColor: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 16.dp)
                .padding(top = 16.dp) // Increase top padding to move text further down
        )
        Button(
            onClick = onClick,
            shape = CircleShape,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = buttonColor // Use the passed button color
            ),
            modifier = Modifier.size(72.dp)
        ) {
            Icon(
                imageVector = icon, // Use the passed icon
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
