package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MockDataProvider
import com.example.models.Vessel
import com.example.models.VesselStatus
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VesselListScreen(onVesselClick: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<VesselStatus?>(null) }

    val filteredList = MockDataProvider.vesselList.filter {
        val matchesSearch = it.name.contains(searchQuery, ignoreCase = true) || 
                            it.mmsi.contains(searchQuery, ignoreCase = true)
        val matchesStatus = selectedStatus == null || it.status == selectedStatus
        matchesSearch && matchesStatus
    }

    Column(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("搜尋船名或 MMSI...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceWhite,
                unfocusedContainerColor = SurfaceWhite,
                unfocusedBorderColor = BorderLight,
                focusedBorderColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(12.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedStatus == null,
                    onClick = { selectedStatus = null },
                    label = { Text("全部") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
            items(VesselStatus.values()) { status ->
                FilterChip(
                    selected = selectedStatus == status,
                    onClick = { selectedStatus = status },
                    label = { Text(status.displayName) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList) { vessel ->
                VesselCardTheme(vessel = vessel, onClick = { onVesselClick(vessel.id) })
            }
        }
    }
}

@Composable
fun VesselCardTheme(vessel: Vessel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(1.dp, BorderLight, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = vessel.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextDark
                    )
                    Text(
                        text = "MMSI: ${vessel.mmsi} | ${vessel.type.displayName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextGray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                StatusBadgeTheme(status = vessel.status)
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderLight)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                VesselInfoItem("目的地", vessel.destination)
                VesselInfoItem("ETA", vessel.eta)
                VesselInfoItem("航速", "${vessel.speed} kn")
            }
        }
    }
}

@Composable
fun VesselInfoItem(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextLight)
        Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = TextDark)
    }
}

@Composable
fun StatusBadgeTheme(status: VesselStatus) {
    val (textColor, bgColor) = when(status) {
        VesselStatus.INBOUND -> StatusInboundColor to StatusInboundBg
        VesselStatus.OUTBOUND -> StatusOutboundColor to StatusOutboundBg
        VesselStatus.DOCKED -> StatusDockedColor to StatusDockedBg
        VesselStatus.WAITING -> StatusWaitingColor to StatusWaitingBg
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.displayName,
            color = textColor,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp)
        )
    }
}
