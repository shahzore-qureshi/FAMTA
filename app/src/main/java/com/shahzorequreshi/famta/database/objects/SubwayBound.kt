package com.shahzorequreshi.famta.database.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_bound", foreignKeys = [
    (ForeignKey(entity = SubwayService::class, parentColumns = arrayOf("id"), childColumns = arrayOf("service_id")))
])
data class SubwayBound(val name: String, val direction: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0

    @ColumnInfo(name = "service_id")
    val serviceId: String = ""

    override fun toString(): String {
        return name
    }
}