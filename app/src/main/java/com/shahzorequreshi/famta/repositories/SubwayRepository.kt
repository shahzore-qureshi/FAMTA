package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository that handles all subway-related information.
 */
@Singleton
class SubwayRepository {
    @Inject lateinit var mDatabase: AppDatabase

    init {
        MainApplication.component.inject(this)
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        return mDatabase.getSubwayLineDao().get()
    }

    fun getSubwayServices(): LiveData<List<SubwayService>>? {
        return mDatabase.getSubwayServiceDao().get()
    }
}