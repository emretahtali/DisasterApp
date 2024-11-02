package com.example.disasterapp

data class LocationData(
    var latitude: Double,
    var longitude: Double
)

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

data class GeocodingResult(
    val formatted_adress: String
)
