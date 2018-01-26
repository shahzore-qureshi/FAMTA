package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Represents a subway line (ex. blue line, orange line).
 */
@Entity(tableName = "subway_line")
data class SubwayLine(@PrimaryKey val _id: String, val name: String) : Serializable {
    override fun toString(): String {
        return name
    }
}