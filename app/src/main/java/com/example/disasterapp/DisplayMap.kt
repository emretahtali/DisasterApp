package com.example.disasterapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.example.disasterapp.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.platform.LocalContext
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color



@Composable
fun DisplayMap(
    location: LocationData,
    userState: String?,
    viewModel: LocationViewModel
) {
    val context = LocalContext.current
    var iconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.restaurantpin)
    if (userState == "Barınma") iconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shelterpin)
    else if (userState == "İnternet") iconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.internetpin)

    val smallMarkerIcon = Bitmap.createScaledBitmap(iconBitmap, 100, 100, false) // İkonun boyutlarını ayarlayın

    var userLocation by remember { mutableStateOf(LatLng(location.latitude, location.longitude)) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 10f)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleMap (
            modifier = Modifier
                .weight(1f),
            cameraPositionState = cameraPositionState,
            onMapClick = { userLocation = it }
        ) {
            if (userState != null)
            {
                Marker(
                    state = MarkerState(userLocation),
                    icon = BitmapDescriptorFactory.fromBitmap(smallMarkerIcon)
                )
                location.latitude = userLocation.latitude
                location.longitude = userLocation.longitude
            }

            else {
                val helpers = viewModel.getHelpers()
                helpers.forEach { helper ->
                    // Her bir yemek yardımı sağlayan yer için haritaya bir işaretçi (marker) eklendi
                    val position =
                        helper.location?.let { LatLng(it.latitude, helper.location.longitude) }
                    position?.let { MarkerState(it) }?.let { Marker(state = it) }
                }
            }
        }
    }
}
