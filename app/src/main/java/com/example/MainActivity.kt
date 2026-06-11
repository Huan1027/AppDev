package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.navigation.Screen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MapScreen
import com.example.ui.screens.VesselDetailScreen
import com.example.ui.screens.VesselListScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Screen.Dashboard,
        Screen.VesselList,
        Screen.Map
    )

    // Hide bottom bar on detail screen
    val isDetailScreen = currentRoute?.startsWith("vessel_detail") == true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!isDetailScreen) {
                NavigationBar(
                    containerColor = com.example.ui.theme.SurfaceWhite,
                    contentColor = com.example.ui.theme.TextLight,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
                            selected = currentRoute == screen.route,
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                selectedIconColor = com.example.ui.theme.PrimaryBlue,
                                unselectedIconColor = com.example.ui.theme.TextLight,
                                selectedTextColor = com.example.ui.theme.PrimaryBlue,
                                unselectedTextColor = com.example.ui.theme.TextLight,
                                indicatorColor = com.example.ui.theme.SurfaceWhite
                            ),
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }
            composable(Screen.VesselList.route) {
                VesselListScreen(onVesselClick = { vesselId ->
                    navController.navigate(Screen.VesselDetail.createRoute(vesselId))
                })
            }
            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.VesselDetail.route) { backStackEntry ->
                val vesselId = backStackEntry.arguments?.getString("vesselId") ?: ""
                VesselDetailScreen(
                    vesselId = vesselId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

