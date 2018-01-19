package com.shahzorequreshi.famta.database.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Entity(tableName = "subway_line")
data class SubwayLine(@ColumnInfo(name = "name") var name: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}