package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway service (ex. service A, service C).
 */
@Entity(tableName = "subway_service")
data class SubwayService(
        @PrimaryKey val _id: String,
        val name: String,
        @ColumnInfo(name = "line_id") val lineId: String) : Serializable {
    override fun toString(): String {
        return name
    }
}