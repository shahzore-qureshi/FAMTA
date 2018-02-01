package com.shahzorequreshi.famta.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.shahzorequreshi.famta.database.converters.DateConverter
import com.shahzorequreshi.famta.database.converters.ListConverter
import com.shahzorequreshi.famta.database.dao.*
import com.shahzorequreshi.famta.database.entities.*
import javax.inject.Singleton

/**
 * Database that holds all application persistent information.
 */
@Database(entities = [
    SubwayService::class,
    SubwayBound::class,
    SubwayStation::class,
    SubwayTime::class], version = 1)
@TypeConverters(DateConverter::class, ListConverter::class)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app-database"
    }
    abstract fun getSubwayServiceDao(): SubwayServiceDao
    abstract fun getSubwayBoundDao(): SubwayBoundDao
    abstract fun getSubwayStationDao(): SubwayStationDao
    abstract fun getSubwayTimeDao(): SubwayTimeDao
}