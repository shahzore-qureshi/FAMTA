package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway service (ex. service A, service C).
 */
@Entity(tableName = "subway_service")
data class SubwayService(
        @PrimaryKey val id: String,
        val name: String,
        val bound_ids: List<String>,
        var last_updated: Long = 0) : Serializable {
    override fun toString(): String {
        return name
    }
}