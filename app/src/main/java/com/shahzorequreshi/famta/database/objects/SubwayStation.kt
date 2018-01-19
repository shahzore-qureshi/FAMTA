package com.shahzorequreshi.famta.database.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.Date

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_station", foreignKeys = [
    (ForeignKey(entity = SubwayBound::class, parentColumns = arrayOf("id"), childColumns = arrayOf("bound_id")))
])
data class SubwayStation(@PrimaryKey val id: String, val name: String) : Serializable {
    @ColumnInfo(name = "bound_id")
    val boundId: Long = 0

    override fun toString(): String {
        return name
    }
}