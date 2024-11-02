package com.example.disasterapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.zIndex

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
    val isShadowApplied = remember { mutableStateOf(false) }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            // Harita bileşeni (MapScreen)
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

            // Harita üzerinde yarı saydam bir gölge katmanı
            if (isShadowApplied.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000)) // Yarı saydam siyah renk ile gölge efekti
                        .zIndex(1f)
                )
            }

            // AddLocation bileşeni, gölgenin üstünde olacak şekilde zIndex ile yerleştirildi
            Box(modifier = Modifier.zIndex(2f)) {
                AddLocation (
                    isDropdownExpanded = isDropdownExpanded,
                    contentPadding = contentPadding,
                    isShadowApplied,
                    onHelpTypeSelected = {
                        selectedHelpType ->
                    userState = selectedHelpType
                })
            }

            // Add Button
            if (userState == null) {
                FloatingActionButton(
                    onClick = {
                        isDropdownExpanded.value = !isDropdownExpanded.value
                        isShadowApplied.value = !isShadowApplied.value
                    },
                    shape = CircleShape,
                    containerColor = Color(0xFFB33F00),
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Sağ altta konumlandırma
                        .padding(16.dp)
                        .size(80.dp)
                        .zIndex(3f) // Gölge altında kalmasını sağlar
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add item",
                        tint = Color.White
                    )
                }
            }

            else {
                // Confirm Button
                FloatingActionButton(
                    onClick = {
                        userState = null
                        navController.currentBackStackEntry?.savedStateHandle?.set("lat", location?.latitude)
                        navController.currentBackStackEntry?.savedStateHandle?.set("lng", location?.longitude)
                        navController.navigate(Screen.SpecScreen.route)
                    },
                    shape = CircleShape,
                    containerColor = Color(0xFFB33F00),
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Sağ altta konumlandırma
                        .padding(16.dp)
                        .size(80.dp)
                        .zIndex(3f) // Gölge altında kalmasını sağlar
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "confirm location",
                        tint = Color.White
                    )
                }

                // Cancel Button
                FloatingActionButton(
                    onClick = {
                        userState = null
                    },
                    shape = CircleShape,
                    containerColor = Color(0xFFB33F00),
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Sağ altta konumlandırma
                        .padding(16.dp)
                        .size(80.dp)
                        .zIndex(3f) // Gölge altında kalmasını sağlar
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "cancel",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
