package com.example.disasterapp

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddLocation(
    isDropdownExpanded: MutableState<Boolean>,
    contentPadding: PaddingValues,
    isShadowApplied: MutableState<Boolean>,
    onHelpTypeSelected: (String) -> Unit
) {
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
                    .padding(bottom = 112.dp, end = 8.dp)
                    .background(Color.Transparent)
            ) {
                CustomDropdownItem(
                    text = "Gıda",
                    buttonColor = Color(0xFF00B3AD),
                    iconResId = R.drawable.restaurant,
                    onClick = {
                        isDropdownExpanded.value = false
                        isShadowApplied.value = false
                        onHelpTypeSelected("Gıda")
                    }
                )
                CustomDropdownItem(
                    text = "Isınma",
                    buttonColor = Color(0xFF006663),
                    iconResId = R.drawable.shelter,
                    onClick = {
                        isDropdownExpanded.value = false
                        isShadowApplied.value = false
                        onHelpTypeSelected("Isınma")
                    }
                )
                CustomDropdownItem(
                    text = "İnternet",
                    buttonColor = Color(0xFFFF6B1A),
                    iconResId = R.drawable.internet,
                    onClick = {
                        isDropdownExpanded.value = false
                        isShadowApplied.value = false
                        onHelpTypeSelected("İnternet")
                    }
                )
            }
        }
    }
}

@Composable
fun CustomDropdownItem(
    text: String,
    buttonColor: Color,
    iconResId: Int,
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
            color = Color.White,
            text = text,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 16.dp)
                .padding(top = 16.dp)
        )

        Button(
            onClick = onClick,
            shape = CircleShape,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            modifier = Modifier.size(72.dp) // Fixed button size for all items
        ) {
            // Set icon size conditionally based on the text value
            val iconSize = if (text == "Isınma") 48.dp else 24.dp
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(iconSize) // Only increase the image size for "Isınma"
            )
        }
    }
}
