package com.example.disasterapp

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "add item")
            }
        }
    ) { contentPadding ->
        MapScreen(
            locationUtils = locationUtils,
            viewModel = viewModel,
            navController = navController,
            context = context,
            address = viewModel.address.value.firstOrNull()?.formatted_adress ?: "No Address",
            location = viewModel.location.value
        )
        AddLocation(isDropdownExpanded = isDropdownExpanded, contentPadding = contentPadding)
    }
}