package com.example.disasterapp

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color


@Composable
fun MainScreen(
    isDropdownExpanded: MutableState<Boolean>,
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String,
    location: LocationData?
){
    var userState by remember { mutableStateOf<String?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isDropdownExpanded.value = !isDropdownExpanded.value },
                shape = CircleShape,
                containerColor = Color(0xFFB33F00),
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add item",tint = Color.White)
            }
        }
    ) { contentPadding ->
        MapScreen(
            locationUtils = locationUtils,
            viewModel = viewModel,
            navController = navController,
            context = context,
            address = viewModel.address.value.firstOrNull()?.formatted_adress ?: "No Address",
            location = viewModel.location.value,
            userState = userState
        )
        
        if (userState != null) {
            Button(onClick = {
                userState = null
            }) {
                Text(text = "Done")
            }
        }

        AddLocation(
            isDropdownExpanded = isDropdownExpanded,
            contentPadding = contentPadding,
            onHelpTypeSelected = {
                selectedHelpType ->
                userState = selectedHelpType
            })
    }
}