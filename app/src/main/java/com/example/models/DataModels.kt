package com.example.models

data class Vessel(
    val id: String,
    val name: String,
    val mmsi: String,
    val type: VesselType,
    val status: VesselStatus,
    val destination: String,
    val eta: String,
    val speed: Double, // in knots
    val course: Double, // in degrees
    val latitude: Double,
    val longitude: Double,
    val dockedAt: String? = null
)

enum class VesselType(val displayName: String) {
    CONTAINER("貨櫃船"),
    TANKER("油輪"),
    FISHING("漁船"),
    PASSENGER("客船"),
    CARGO("散裝船")
}

enum class VesselStatus(val displayName: String) {
    INBOUND("進港"),
    OUTBOUND("出港"),
    DOCKED("停泊"),
    WAITING("等待靠岸")
}

data class PortStats(
    val total: Int,
    val inbound: Int,
    val outbound: Int,
    val docked: Int,
    val waiting: Int
)

data class EnvironmentInfo(
    val weather: String,
    val temperature: String,
    val windSpeed: String,
    val visibility: String
)
