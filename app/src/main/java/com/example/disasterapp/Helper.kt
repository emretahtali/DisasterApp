package com.example.disasterapp

data class Helper(
    val name: String = "",
    val contactInfo: String = "",
    val helpType: String = "",
    val availability: Boolean = true,
    val address: String = "",
    val addressDescription: String = "",
    val currentCount: Int = 0,
    val maxCapacity: Int = 0
)
