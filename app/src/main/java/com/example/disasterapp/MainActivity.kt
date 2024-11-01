package com.example.disasterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.disasterapp.ui.theme.DisasterAppTheme
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity()
{
    private val isDropdownExpanded = mutableStateOf(false)
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // Firebase Auth ve Firestore'u başlatın
        auth = Firebase.auth
        db = Firebase.firestore

        // Anonim giriş yapın ve ardından uploadHelper işlevini çağırın
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Giriş başarılı, veri ekleme işlemini başlat
                uploadHelper()
            } else {
                // Giriş başarısız oldu
                Toast.makeText(this, "Giriş başarısız oldu: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }

        setContent {
            DisasterAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(isDropdownExpanded)
                }
            }
        }
    }

    private fun uploadHelper() {
        val newHelper = Helper(
            name = "Anonim Kullanıcı",
            contactInfo = "123456789",
            helpType = "Yemek",
            availability = true,
            address = "Ahmet Haşim Sokak No:15, Kadıköy, Istanbul",
            addressDescription = "3. kat, sağdaki daire",
            currentCount = 0,
            maxCapacity = 10
        )

        db.collection("helpers").add(newHelper)
            .addOnSuccessListener {
                Toast.makeText(this, "Helper başarıyla eklendi!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Hata oluştu: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}

@Composable
fun Navigation(isDropdownExpanded: MutableState<Boolean>) {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(
                isDropdownExpanded = isDropdownExpanded,
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_adress ?: "No Address",
                location = viewModel.location.value
            )
        }
    }
}