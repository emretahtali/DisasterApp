package com.example.disasterapp

import android.content.Context
import androidx.compose.foundation.Image
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore

@Composable
fun MainScreen(
    isDropdownExpanded: MutableState<Boolean>,
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String,
    location: LocationData?,
    helpers : List<Helper>,
    db: FirebaseFirestore
){


    var userState by remember { mutableStateOf<String?>(null) }
    val isShadowApplied = remember { mutableStateOf(false) }

    var showYemek by remember { mutableStateOf(true) }
    var showBarinma by remember { mutableStateOf(true) }
    var showInternet by remember { mutableStateOf(true) }


    val helpers by viewModel.helpers

    // Filtre değişikliklerini kontrol etme
    LaunchedEffect(showYemek, showBarinma, showInternet) {
        viewModel.fetchFilteredHelpers(
            db,
            showYemek,
            showBarinma,
            showInternet,
            onFailure = { exception ->
                Toast.makeText(context, "Veri alınamadı: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        )
    }


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
                userState = userState,
                db = db
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent) // Yarı saydam beyaz arka plan
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { showYemek = !showYemek },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showYemek) Color(0xFF1565C0) else Color(0xFFBB86FC).copy(alpha = 0.5f) // Tıklanınca yarı saydam hale gelir
                        )
                    ) {
                        Text(text = "Yemek")
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { showBarinma = !showBarinma },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showBarinma) Color(0xFF1565C0) else Color(0xFF1E88E5).copy(alpha = 0.5f) // Tıklanınca yarı saydam koyu mavi
                        )
                    ) {
                        Text(text = "Barınma")
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { showInternet = !showInternet },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (showInternet) Color(0xFF1565C0) else Color(0xFF42A5F5).copy(alpha = 0.5f) // Tıklanınca yarı saydam koyu mavi
                        )
                    ) {
                        Text(text = "İnternet")
                    }
                }
            }



            if (isShadowApplied.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000))
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
                    navController.currentBackStackEntry?.savedStateHandle?.set("helpType", selectedHelpType)
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
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(80.dp)
                        .zIndex(3f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.handshake),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
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
                    containerColor = Color(0xFF03A64A),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(80.dp)
                        .zIndex(3f)
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
                        navController.currentBackStackEntry?.savedStateHandle?.set("helpType", null)
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
