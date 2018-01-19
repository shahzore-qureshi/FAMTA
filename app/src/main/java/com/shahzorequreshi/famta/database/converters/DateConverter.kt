package com.shahzorequreshi.famta.database.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Shahzore Qureshi on 1/15/18.
 */
object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}