package com.shahzorequreshi.famta.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.threads.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module that provides singletons.
 */
@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Singleton
    fun providesDatabase(): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                .build()
    }

    @Provides
    @Singleton
    fun providesSubwayRepository(): SubwayRepository {
        return SubwayRepository()
    }
}