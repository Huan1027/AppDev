package com.example.data

import com.example.models.EnvironmentInfo
import com.example.models.PortStats
import com.example.models.Vessel
import com.example.models.VesselStatus
import com.example.models.VesselType

object MockDataProvider {
    val environmentInfo = EnvironmentInfo(
        weather = "晴朗",
        temperature = "28°C",
        windSpeed = "15 knot (西南風)",
        visibility = "10 NM"
    )

    val vesselList = listOf(
        Vessel(
            id = "1",
            name = "YM WISH",
            mmsi = "416486000",
            type = VesselType.CONTAINER,
            status = VesselStatus.INBOUND,
            destination = "Kaohsiung",
            eta = "2026-06-11 14:30",
            speed = 12.5,
            course = 45.0,
            latitude = 22.580,
            longitude = 120.250
        ),
        Vessel(
            id = "2",
            name = "EVER GIVEN",
            mmsi = "353136000",
            type = VesselType.CONTAINER,
            status = VesselStatus.DOCKED,
            destination = "Kaohsiung",
            eta = "2026-06-10 09:00",
            speed = 0.0,
            course = 0.0,
            latitude = 22.560,
            longitude = 120.300,
            dockedAt = "No. 115 Pier"
        ),
        Vessel(
            id = "3",
            name = "FORMOSAN LILY",
            mmsi = "416201000",
            type = VesselType.TANKER,
            status = VesselStatus.OUTBOUND,
            destination = "Singapore",
            eta = "2026-06-18 10:00",
            speed = 14.2,
            course = 210.0,
            latitude = 22.520,
            longitude = 120.200
        ),
        Vessel(
            id = "4",
            name = "STAR FERRY 1",
            mmsi = "416999123",
            type = VesselType.PASSENGER,
            status = VesselStatus.WAITING,
            destination = "Kaohsiung",
            eta = "2026-06-11 16:00",
            speed = 2.1,
            course = 90.0,
            latitude = 22.600,
            longitude = 120.220
        ),
        Vessel(
            id = "5",
            name = "KHS FISHER",
            mmsi = "416888222",
            type = VesselType.FISHING,
            status = VesselStatus.DOCKED,
            destination = "Kaohsiung",
            eta = "2026-06-09 23:00",
            speed = 0.0,
            course = 180.0,
            latitude = 22.580,
            longitude = 120.280,
            dockedAt = "Fishery Port A"
        ),
        Vessel(
            id = "6",
            name = "OOCL TAIWAN",
            mmsi = "477811200",
            type = VesselType.CONTAINER,
            status = VesselStatus.INBOUND,
            destination = "Kaohsiung",
            eta = "2026-06-11 18:45",
            speed = 18.0,
            course = 65.0,
            latitude = 22.500,
            longitude = 120.150
        )
    )

    fun getPortStats(): PortStats {
        val total = vesselList.size
        val inbound = vesselList.count { it.status == VesselStatus.INBOUND }
        val outbound = vesselList.count { it.status == VesselStatus.OUTBOUND }
        val docked = vesselList.count { it.status == VesselStatus.DOCKED }
        val waiting = vesselList.count { it.status == VesselStatus.WAITING }
        return PortStats(total, inbound, outbound, docked, waiting)
    }
}
