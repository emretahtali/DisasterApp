package com.example.disasterapp

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

data class GeocodingResult(
    val formatted_adress: String
)
