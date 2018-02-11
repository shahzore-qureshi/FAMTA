package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway service direction (northbound or southbound).
 */
@Entity(tableName = "subway_bound")
data class SubwayBound(
        @PrimaryKey val id: String,
        val name: String,
        val direction: String,
        var last_updated: Long = 0) : Serializable {
    override fun toString(): String {
        return name
    }
}