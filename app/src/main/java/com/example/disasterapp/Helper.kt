package com.example.disasterapp

import com.google.firebase.firestore.GeoPoint

data class Helper(
    val name: String? = null,
    val helpType: String? = null,
    val description: String? = null,
    val contactInfo: String? = null,
    val availability: Boolean? = null,
    val address: String? = null,
    val maxCapacity: Int? = null,
    val location: GeoPoint? = null,
    val cur: Int? = 0
)
