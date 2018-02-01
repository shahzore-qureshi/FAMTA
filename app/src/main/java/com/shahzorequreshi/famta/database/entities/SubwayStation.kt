package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway station.
 */
@Entity(tableName = "subway_station")
data class SubwayStation(
        @PrimaryKey val id: String,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val service_ids: List<String>) : Serializable {
    override fun toString(): String {
        return name
    }
}