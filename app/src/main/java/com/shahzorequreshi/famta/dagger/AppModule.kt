package com.shahzorequreshi.famta.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.services.SubwayWebService
import com.shahzorequreshi.famta.threads.AppExecutors
import com.twitter.sdk.android.core.Twitter
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
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providesSubwayRepository(): SubwayRepository {
        return SubwayRepository()
    }

    @Provides
    @Singleton
    fun providesSubwayWebService(): SubwayWebService {
        return SubwayWebService()
    }

    @Provides
    @Singleton
    fun providesLocationProvider(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}