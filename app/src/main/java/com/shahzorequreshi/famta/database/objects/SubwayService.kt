package com.shahzorequreshi.famta.database.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_service", foreignKeys = [
    (ForeignKey(entity = SubwayLine::class, parentColumns = arrayOf("id"), childColumns = arrayOf("line_id")))
])
data class SubwayService(val name: String, val drawableId: Int) : Serializable {
    @PrimaryKey
    val id: Long = 0

    @ColumnInfo(name = "line_id")
    val lineId: String = ""

    override fun toString(): String {
        return name
    }
}