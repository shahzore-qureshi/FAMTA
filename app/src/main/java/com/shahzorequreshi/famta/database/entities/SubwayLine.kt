package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway line (ex. line ACE, line BDFM).
 */
@Entity(tableName = "subway_line")
data class SubwayLine(
        @PrimaryKey val id: String,
        val name: String,
        val status: String,
        val events: List<SubwayLineEvent>,
        var last_updated: Long = 0) : Serializable {
    override fun toString(): String {
        return name
    }
}