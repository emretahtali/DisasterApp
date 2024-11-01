package com.example.disasterapp

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AssistanceRepository {
    val db = Firebase.firestore

    private val helpersCollection = db.collection("helpers")

    fun addHelper(helper: Helper, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        helpersCollection.add(helper)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getAllHelpers(onSuccess: (List<Helper>) -> Unit, onFailure: (Exception) -> Unit) {
        helpersCollection.get()
            .addOnSuccessListener { documents ->
                val helpers = documents.mapNotNull { it.toObject(Helper::class.java) }
                onSuccess(helpers)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getHelpersByType(helpType: String, onSuccess: (List<Helper>) -> Unit, onFailure: (Exception) -> Unit) {
        helpersCollection.whereEqualTo("helpType", helpType).get()
            .addOnSuccessListener { documents ->
                val helpers = documents.mapNotNull { it.toObject(Helper::class.java) }
                onSuccess(helpers)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
