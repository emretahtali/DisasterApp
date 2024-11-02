package com.example.disasterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.GeoPoint
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController,helper: Helper) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4E2DE))
            .padding(horizontal = 32.dp)
            .padding(top = 140.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = helper.name ?: "Yardımcı Bilgisi Yok",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "İletişim: ${helper.contactInfo ?: "Bilgi yok"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Yardım Türü: ${helper.helpType ?: "Bilgi yok"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Durum: ${if (helper.availability == true) "Uygun" else "Uygun Değil"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Adres: ${helper.address ?: "Adres bilgisi yok"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Açıklama: ${helper.description ?: "Açıklama yok"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Mevcut Kapasite: ${helper.maxCapacity ?: "Sınırsız"}",
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.MainScreen.route) },
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 12.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD94F04)) // Arka plan rengini #F4E2DE olarak ayarladık
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Geri Dön",
                tint = Color(0xFF025259),
                modifier = Modifier.size(28.dp),
            )
        }
    }
}
