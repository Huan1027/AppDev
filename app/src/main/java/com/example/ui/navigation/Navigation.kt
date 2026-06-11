package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DirectionsBoat
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "總覽", Icons.Outlined.Dashboard)
    object VesselList : Screen("vessel_list", "船舶", Icons.Outlined.DirectionsBoat)
    object Map : Screen("map", "地圖", Icons.Outlined.Explore)
    object VesselDetail : Screen("vessel_detail/{vesselId}", "船舶詳細資料", Icons.Outlined.Dashboard) {
        fun createRoute(vesselId: String) = "vessel_detail/$vesselId"
    }
}
