package com.shahzorequreshi.famta.database.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Converts timestamps to dates and vice versa.
 */
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}