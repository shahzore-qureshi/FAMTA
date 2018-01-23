package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.threads.AppExecutors
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository that handles all subway-related information.
 */
@Singleton
class SubwayRepository {
    @Inject lateinit var mDatabase: AppDatabase
    @Inject lateinit var mExecutors: AppExecutors

    init {
        MainApplication.component.inject(this)

        mExecutors.diskIO().execute {
            setUpDatabase()
        }
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        return mDatabase.getSubwayLineDao().get()
    }

    fun getSubwayServices(subwayLine: SubwayLine): LiveData<List<SubwayService>>? {
        return mDatabase.getSubwayServiceDao().get(subwayLine.id)
    }

    fun getSubwayBounds(subwayService: SubwayService): LiveData<List<SubwayBound>>? {
        return mDatabase.getSubwayBoundDao().get(subwayService.id)
    }

    fun getSubwayStations(subwayBound: SubwayBound): LiveData<List<SubwayStation>>? {
        return mDatabase.getSubwayStationDao().get(subwayBound.id)
    }

    fun getSubwayTimes(subwayStation: SubwayStation, subwayBound: SubwayBound): LiveData<List<SubwayTime>>? {
        return mDatabase.getSubwayTimeDao().get(subwayStation.id, subwayBound.id)
    }

    private fun setUpDatabase() {
        mDatabase.getSubwayLineDao().insert(SubwayLine("blue"), SubwayLine("orange"))

        mDatabase.getSubwayServiceDao().insert(
                SubwayService("A", 1),
                SubwayService("C", 1),
                SubwayService("E", 1))
        mDatabase.getSubwayServiceDao().insert(
                SubwayService("B", 2),
                SubwayService("D", 2),
                SubwayService("F", 2),
                SubwayService("M", 2))
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("207 St, Manhattan", "North", 1),
                SubwayBound("Lefferts Blvd", "South", 1))
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("168 St, Manhattan", "North", 2),
                SubwayBound("Euclid Avenue", "South", 2))
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("Jamaica Center", "North", 3),
                SubwayBound("World Trade Center", "South", 3))
        mDatabase.getSubwayStationDao().insert(
                SubwayStation("101N", "238 St", 1),
                SubwayStation("104N", "231 St", 1),
                SubwayStation("106N", "225 St", 1),
                SubwayStation("106S", "225 St", 2),
                SubwayStation("104S", "231 St", 2),
                SubwayStation("101S", "238 St", 2))
        mDatabase.getSubwayTimeDao().insert(
                SubwayTime(Date(1516743420000), "101N", 1),
                SubwayTime(Date(1516744200000), "101N", 1),
                SubwayTime(Date(1516748100000), "101N", 1),
                SubwayTime(Date(), "101S", 2),
                SubwayTime(Date(), "101S", 2),
                SubwayTime(Date(), "101S", 2))
    }
}