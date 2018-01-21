package com.shahzorequreshi.famta.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import com.shahzorequreshi.famta.database.converters.DateConverter
import com.shahzorequreshi.famta.database.dao.SubwayLineDao
import com.shahzorequreshi.famta.database.dao.SubwayServiceDao
import com.shahzorequreshi.famta.database.entities.*
import javax.inject.Singleton

/**
 * Database that holds all application persistent information.
 */
@Database(entities = [SubwayLine::class, SubwayService::class], version = 1)
@TypeConverters(DateConverter::class)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app-database"
    }
    abstract fun getSubwayLineDao(): SubwayLineDao
    abstract fun getSubwayServiceDao(): SubwayServiceDao
}