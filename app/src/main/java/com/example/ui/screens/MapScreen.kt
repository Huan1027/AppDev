package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.MockDataProvider
import com.example.models.VesselStatus
import com.example.ui.theme.*

@Composable
fun MapScreen() {
    val vessels = MockDataProvider.vesselList

    Column(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(PrimaryBlue)
                .padding(top = 24.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "港口地圖模擬",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEAF5FF)) // light blue ocean
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // Draw simple port area
                    drawRect(
                        color = Color(0xFFD2D6DC), // slate-300
                        topLeft = Offset(width * 0.7f, 0f),
                        size = androidx.compose.ui.geometry.Size(width * 0.3f, height)
                    )

                    // Draw vessels
                    vessels.forEach { vessel ->
                        val xPos = ((vessel.longitude - 120.15) / 0.15 * width * 0.6f).toFloat() 
                        val yPos = (height - (vessel.latitude - 22.5) / 0.1 * height).toFloat()

                        val iconColor = when(vessel.status) {
                            VesselStatus.INBOUND -> StatusInboundColor
                            VesselStatus.OUTBOUND -> StatusOutboundColor
                            VesselStatus.DOCKED -> StatusDockedColor
                            VesselStatus.WAITING -> StatusWaitingColor
                        }

                        drawCircle(
                            color = iconColor,
                            radius = 16f,
                            center = Offset(xPos, yPos)
                        )

                        // Draw vessel name label
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.DKGRAY
                                textSize = 28f
                                textAlign = android.graphics.Paint.Align.CENTER
                                isFakeBoldText = true
                            }
                            canvas.nativeCanvas.drawText(vessel.name, xPos, yPos - 25f, paint)
                        }
                    }
                }

                // Legend
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.9f), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, BorderLight, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text("圖例", fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.padding(bottom = 8.dp))
                    LegendItemTheme("進港中", StatusInboundColor)
                    LegendItemTheme("出港中", StatusOutboundColor)
                    LegendItemTheme("已靠泊", StatusDockedColor)
                    LegendItemTheme("等待靠岸", StatusWaitingColor)
                }
            }
        }
    }
}

@Composable
fun LegendItemTheme(label: String, color: Color) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(10.dp).background(color, shape = androidx.compose.foundation.shape.CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextDark)
    }
}
