package com.example.disasterapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class HelpFormActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_form)

        db = FirebaseFirestore.getInstance()

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val contactInfoEditText = findViewById<EditText>(R.id.contactInfoEditText)
        val helpTypeEditText = findViewById<EditText>(R.id.helpTypeEditText)
        val addressEditText = findViewById<EditText>(R.id.addressEditText)
        val addressDescriptionEditText = findViewById<EditText>(R.id.addressDescriptionEditText)
        val maxCapacityEditText = findViewById<EditText>(R.id.maxCapacityEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val contactInfo = contactInfoEditText.text.toString()
            val helpType = helpTypeEditText.text.toString()
            val address = addressEditText.text.toString()
            val addressDescription = addressDescriptionEditText.text.toString()
            val maxCapacity = maxCapacityEditText.text.toString().toIntOrNull() ?: 0

            if (name.isNotEmpty() && contactInfo.isNotEmpty() && helpType.isNotEmpty() && address.isNotEmpty()) {
                saveHelper(name, contactInfo, helpType, address, addressDescription, maxCapacity)
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveHelper(
        name: String,
        contactInfo: String,
        helpType: String,
        address: String,
        addressDescription: String,
        maxCapacity: Int,
        location: GeoPoint? = null
    ) {
        val newHelper = Helper(
            name = name,
            contactInfo = contactInfo,
            helpType = helpType,
            availability = true,
            address = address,
            addressDescription = addressDescription,
            currentCount = 0,
            maxCapacity = maxCapacity,
            location = location
        )

        db.collection("helpers").add(newHelper)
            .addOnSuccessListener {
                Toast.makeText(this, "Yardım kaydedildi!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Hata: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}
