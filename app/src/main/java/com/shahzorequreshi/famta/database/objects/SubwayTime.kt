package com.shahzorequreshi.famta.database.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey

import java.io.Serializable
import java.util.Date

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_time", foreignKeys = [
    ForeignKey(entity = SubwayStation::class, parentColumns = arrayOf("id"), childColumns = arrayOf("station_id"))
])
data class SubwayTime(@ColumnInfo(name = "arrival_time") val arrivalTime: Date) : Serializable {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0

    @ColumnInfo(name = "station_id")
    val stationId: String = ""

    override fun toString(): String {
        return arrivalTime.toString()
    }
}