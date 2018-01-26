package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.services.SubwayWebService
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
    @Inject lateinit var mSubwayWebService: SubwayWebService

    init {
        MainApplication.component.inject(this)
        setUpDatabase()
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        return mDatabase.getSubwayLineDao().get()
    }

    fun getSubwayServices(subwayServiceIds: List<String>): LiveData<List<SubwayService>>? {
        return mDatabase.getSubwayServiceDao().get(subwayServiceIds)
    }

    fun getSubwayBounds(subwayService: SubwayService): LiveData<List<SubwayBound>>? {
        return mDatabase.getSubwayBoundDao().get(subwayService._id)
    }

    fun getSubwayStations(): LiveData<List<SubwayStation>>? {
        return mDatabase.getSubwayStationDao().get()
    }

    fun getSubwayTimes(subwayStation: SubwayStation, subwayBound: SubwayBound): LiveData<List<SubwayTime>>? {
        return mDatabase.getSubwayTimeDao().get(subwayStation.stop_id, subwayBound.id)
    }

    fun removeSubwayTime(subwayTime: SubwayTime) {
        mExecutors.diskIO().execute { mDatabase.getSubwayTimeDao().delete(subwayTime) }
    }

    private fun setUpDatabase() {
        mExecutors.diskIO().execute {
            val subwayLines = mSubwayWebService.getSubwayLines()
            if(subwayLines.isNotEmpty()) {
                mDatabase.getSubwayLineDao().insert(subwayLines)
            }
            val subwayServices = mSubwayWebService.getSubwayServices()
            if(subwayServices.isNotEmpty()) {
                mDatabase.getSubwayServiceDao().insert(subwayServices)
            }
            val subwayStations = mSubwayWebService.getSubwayStations()
            if(subwayStations.isNotEmpty()) {
                mDatabase.getSubwayStationDao().insert(subwayStations)
            }
        }

        /*
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
                SubwayTime(Date(1516744980000), "101N", 1),
                SubwayTime(Date(1516745040000), "101N", 1),
                SubwayTime(Date(1516748100000), "101N", 1),
                SubwayTime(Date(), "101S", 2),
                SubwayTime(Date(), "101S", 2),
                SubwayTime(Date(), "101S", 2))
        */
    }
}