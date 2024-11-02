package com.example.disasterapp

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    private val _helpers = mutableStateOf<List<Helper>>(emptyList())
    val helpers: State<List<Helper>> get() = _helpers

    fun fetchFilteredHelpers(db: FirebaseFirestore, showYemek: Boolean, showBarinma: Boolean, showInternet: Boolean, onFailure: (Exception) -> Unit) {
        val helpTypes = mutableListOf<String>()
        if (showYemek) helpTypes.add("Yemek")
        if (showBarinma) helpTypes.add("Barınma")
        if (showInternet) helpTypes.add("İnternet")

        db.collection("helpers")
            .whereIn("helpType", helpTypes)
            .get()
            .addOnSuccessListener { result ->
                val helpersList = result.mapNotNull { it.toObject(Helper::class.java) }
                updateHelpers(helpersList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateHelpers(newHelpers: List<Helper>) {
        _helpers.value = newHelpers
    }

    fun getHelpers(): List<Helper> {
        return helpers.value
    }

    fun getFoodHelpers(): List<Helper> {
        return helpers.value.filter { it.helpType == "Yemek" }
    }

    fun getShelterHelpers(): List<Helper> {
        return helpers.value.filter { it.helpType == "Barınma" }
    }

    fun getWifiHelpers(): List<Helper> {
        return helpers.value.filter { it.helpType == "İnternet" }
    }


    fun fetchHelpers(db: FirebaseFirestore, onFailure: (Exception) -> Unit) {
        db.collection("helpers")
            .get()
            .addOnSuccessListener { result ->
                val helpersList = result.mapNotNull { it.toObject(Helper::class.java) }
                updateHelpers(helpersList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

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
    data object DetailScreen:Screen("detailScreen")
}