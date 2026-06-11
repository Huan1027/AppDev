package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.MockDataProvider
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VesselDetailScreen(vesselId: String, onBack: () -> Unit) {
    val vessel = MockDataProvider.vesselList.find { it.id == vesselId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("船舶詳細資料", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = SurfaceWhite,
                    navigationIconContentColor = SurfaceWhite
                )
            )
        },
        containerColor = AppBackground
    ) { padding ->
        if (vessel != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = vessel.name,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = TextDark
                            )
                            StatusBadgeTheme(status = vessel.status)
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        DetailRow("MMSI", vessel.mmsi)
                        DetailRow("船舶類型", vessel.type.displayName)
                        DetailRow("經緯度", "${vessel.latitude}, ${vessel.longitude}")
                        DetailRow("航速", "${vessel.speed} 節")
                        DetailRow("航向", "${vessel.course}°")
                        DetailRow("目的港", vessel.destination)
                        DetailRow("預計抵達時間 (ETA)", vessel.eta)
                        if (vessel.dockedAt != null) {
                            DetailRow("停靠碼頭", vessel.dockedAt)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("找不到船舶資料", color = TextGray)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextLight, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = TextDark, fontWeight = FontWeight.Medium)
        HorizontalDivider(modifier = Modifier.padding(top = 10.dp), color = BorderLight)
    }
}
