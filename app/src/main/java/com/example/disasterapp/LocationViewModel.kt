package com.example.disasterapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    fun fetchAddress(latlng: String) {
        try
        {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyAvydAT0nUt6OQSsOfsjX5ptCuQADiYfKQ"
                )
                _address.value = result.results
            }
        }
        catch (e:Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
}

sealed class Screen(val route : String) {
    data object MainScreen:Screen("mainScreen")
    data object SpecScreen:Screen("specScreen")
}