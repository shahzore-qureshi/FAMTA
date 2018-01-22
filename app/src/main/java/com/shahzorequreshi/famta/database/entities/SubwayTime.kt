package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey

import java.io.Serializable
import java.util.Date

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_time")
data class SubwayTime(
        @ColumnInfo(name = "arrival_time") val arrivalTime: Date,
        @ColumnInfo(name = "station_id") val stationId: String,
        @ColumnInfo(name = "bound_id") val boundId: Long) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return arrivalTime.toString()
    }
}