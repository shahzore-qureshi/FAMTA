package com.shahzorequreshi.famta.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import java.io.Serializable
import java.util.Date

/**
 * Represents the arrival or departure time of a subway train.
 */
@Entity(tableName = "subway_time")
data class SubwayTime(
        @PrimaryKey val _id: String,
        @ColumnInfo(name = "station_id") val stationId: String,
        @ColumnInfo(name = "service_id") val serviceId: String,
        @ColumnInfo(name = "bound_id") val boundId: String,
        @ColumnInfo(name = "arrival_time") val arrivalTime: Long) : Serializable {
    override fun toString(): String {
        var timeDifference = Math.round((arrivalTime - Date().time) / 1000f)
        if(timeDifference < 0) timeDifference = 0
        var stringToPrint = ""
        when {
            timeDifference == 0 -> stringToPrint = "Train has arrived"
            timeDifference == 1 -> stringToPrint += timeDifference.toString() + " second away"
            timeDifference in 2..59 -> stringToPrint += timeDifference.toString() + " seconds away"
            timeDifference == 60 -> stringToPrint += (Math.round(timeDifference / 60f)).toString() + " minute away"
            timeDifference in 61..3599 -> stringToPrint += (Math.round(timeDifference / 60f)).toString() + " minutes away"
            timeDifference in 3600..7199 -> stringToPrint += (Math.round(timeDifference / 3600f)).toString() + " hour away"
            timeDifference >= 7200 -> stringToPrint += (Math.round(timeDifference / 3600f)).toString() + " hours away"
        }
        return stringToPrint
    }
}