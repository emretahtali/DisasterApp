package com.example.disasterapp

import com.google.firebase.firestore.GeoPoint

import com.google.firebase.firestore.FirebaseFirestore

class AssistanceRepository {
    val db = FirebaseFirestore.getInstance()

    fun fetchHelpers(onSuccess: (List<Helper>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("helpers")
            .get()
            .addOnSuccessListener { result ->
                val helpers = result.mapNotNull { it.toObject(Helper::class.java) }
                onSuccess(helpers)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
    /*
    // Firestore'dan verileri çek ve haritada göster
    repository.fetchHelpers(
    onSuccess = { helpers ->
        // Her helper konumu için bir marker (işaretçi) ekler
        for (helper in helpers) {
            val geoPoint = helper.location
            if (geoPoint != null) {
                val latLng = LatLng(geoPoint.latitude, geoPoint.longitude)
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(helper.helpType) // İşaretçi başlığı
                        .snippet("${helper.name}, ${helper.contactInfo}") // İşaretçi alt açıklaması
                )
            }
        }

        // İlk konuma yakınlaştırma yap
        helpers.firstOrNull()?.location?.let { firstGeoPoint ->
            val firstLatLng = LatLng(firstGeoPoint.latitude, firstGeoPoint.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 10f))
        }
    },
    onFailure = { exception ->
        // Hata oluştuğunda Toast mesajı göster
        Toast.makeText(this, "Veriler alınamadı: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
    }
    )
    */

}
