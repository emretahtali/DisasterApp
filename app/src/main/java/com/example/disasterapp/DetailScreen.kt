package com.example.disasterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Information(val label: String, val value: String)

@Composable
fun DetailScreen(navController: NavController, helper: Helper) {
    val informationItems = listOf(
        Information("Yardım Türü", helper.helpType ?: "Bilgi yok"),
        Information("Açıklama", helper.description ?: "Açıklama yok"),
        Information("Durum", if (helper.availability == true) "Uygun" else "Uygun Değil"),
        Information("Adres", helper.address ?: "Adres bilgisi yok"),
        Information("İletişim", helper.contactInfo ?: "Bilgi yok"),
        Information("Mevcut Kapasite", "${helper.currentCount ?: 0}/${helper.maxCapacity ?: "Sınırsız"}")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4E2DE))
            .padding(horizontal = 32.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp), // Reduced padding above the title
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = helper.name ?: "Yardımcı Bilgisi Yok",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(informationItems) { info ->
                InformationRow(label = info.label, value = info.value)
            }

            item {
                Spacer(modifier = Modifier.height(60.dp)) // Space to avoid overlap with the button
            }
        }

        Button(
            onClick = { navController.navigate(Screen.MainScreen.route) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD94F04))
        ) {
            Text("Geri Dön", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun InformationRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Gray // Use a lighter color for the label
        )
        Text(
            text = value,
            fontSize = 18.sp
        )
    }
}