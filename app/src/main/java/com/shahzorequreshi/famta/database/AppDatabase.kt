package com.shahzorequreshi.famta.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.shahzorequreshi.famta.database.converters.DateConverter
import com.shahzorequreshi.famta.database.dao.SubwayLineDao
import com.shahzorequreshi.famta.database.objects.*
import javax.inject.Singleton

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Database(entities = [
    (SubwayLine::class)], version = 1)
@TypeConverters(DateConverter::class)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile private var mInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                mInstance ?: synchronized(this) {
                    mInstance ?: buildInMemoryDatabase(context).also { mInstance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app-database")
                        .build()

        private fun buildInMemoryDatabase(context: Context) =
                Room.inMemoryDatabaseBuilder(context.applicationContext,
                        AppDatabase::class.java)
                        .build()
    }

    abstract fun getSubwayLineDao(): SubwayLineDao
    //abstract fun getSubwayStationDao(): SubwayStationDao
}