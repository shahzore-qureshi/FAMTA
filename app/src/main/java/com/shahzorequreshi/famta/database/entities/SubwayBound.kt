package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway service direction (northbound or southbound).
 */
@Entity(tableName = "subway_bound")
data class SubwayBound(val name: String, val direction: String) : Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return name
    }
}