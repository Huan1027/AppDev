package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockDataProvider
import com.example.ui.theme.*

@Composable
fun DashboardScreen() {
    val stats = MockDataProvider.getPortStats()
    val env = MockDataProvider.environmentInfo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        // App Bar / Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(PrimaryBlue)
                .padding(top = 24.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "高雄港船舶動態系統",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "Smart Port Management v2.4",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }

                // Weather / Env Info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Outlined.WbSunny, contentDescription = null, tint = Color(0xFFBFDBFE), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(env.temperature, color = Color.White, style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                    Box(modifier = Modifier.width(1.dp).height(12.dp).background(Color.White.copy(alpha = 0.2f)))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1.5f)) {
                        Icon(Icons.Outlined.Air, contentDescription = null, tint = Color(0xFFBFDBFE), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(env.windSpeed, color = Color.White, style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                    Box(modifier = Modifier.width(1.dp).height(12.dp).background(Color.White.copy(alpha = 0.2f)))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1.2f)) {
                        Icon(Icons.Outlined.Visibility, contentDescription = null, tint = Color(0xFFBFDBFE), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(env.visibility, color = Color.White, style = MaterialTheme.typography.labelMedium, maxLines = 1)
                    }
                }
            }
        }

        // Dashboard Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .offset(y = (-16).dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    StatCardTheme("船舶總數", stats.total.toString(), PrimaryBlue)
                }
                item {
                    StatCardTheme("等待靠岸", stats.waiting.toString(), StatusWaitingColor)
                }
                item {
                    StatCardTheme("進港中", stats.inbound.toString(), StatusInboundColor)
                }
                item {
                    StatCardTheme("出港中", stats.outbound.toString(), StatusOutboundColor)
                }
            }
        }
    }
}

@Composable
fun StatCardTheme(title: String, value: String, valueColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderLight, RoundedCornerShape(16.dp))
            .padding(2.dp) // extra padding for shadow effect equivalent
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = TextGray,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = valueColor
            )
        }
    }
}
