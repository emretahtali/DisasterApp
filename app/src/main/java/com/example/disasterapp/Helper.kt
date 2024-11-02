package com.example.disasterapp

import com.google.firebase.firestore.GeoPoint

data class Helper(
    val name: String? = null,
    val contactInfo: String? = null,
    val helpType: String? = null,
    val availability: Boolean? = null,
    val address: String? = null,
    val addressDescription: String? = null,
    val currentCount: Int? = null,
    val maxCapacity: Int? = null,
    val location: GeoPoint? = null
)
