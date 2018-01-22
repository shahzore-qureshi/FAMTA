package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_station")
data class SubwayStation(
        @PrimaryKey val id: String,
        val name: String,
        @ColumnInfo(name = "bound_id") val boundId: Long) : Serializable {
    override fun toString(): String {
        return name
    }
}