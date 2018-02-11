package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.services.SubwayWebService
import com.shahzorequreshi.famta.threads.AppExecutors
import java.lang.Math.*
import java.util.Date
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
    private var mSubwayStations = MediatorLiveData<List<SubwayStation>>()

    init {
        MainApplication.component.inject(this)
    }

    fun setUserLocation(latitude: Double?, longitude: Double?) {
        mExecutors.diskIO().execute {
            setUpDatabase()
            val lastUpdate = mDatabase.getSubwayStationDao().getLastUpdated()
            if(lastUpdate > 0) {
                val timeSinceLastUpdate = Date().time - lastUpdate
                if (timeSinceLastUpdate >= 900000) { //Update stations every 15 minutes.
                    val subwayStations = mSubwayWebService.getSubwayStations()
                    if (subwayStations.isNotEmpty()) {
                        mDatabase.getSubwayStationDao().update(subwayStations)
                    }
                }
            }
            mSubwayStations.addSource(mDatabase.getSubwayStationDao().get(), {
                var result = it
                if(it != null && latitude != null && longitude != null) {
                    for (station in it.listIterator()) {
                        station.distanceFromUser = calculateDistance(
                                latitude, longitude, station.latitude, station.longitude)
                    }
                    result = it.sortedBy {
                        it.distanceFromUser
                    }
                    if(result.size > 10) {
                        result = result.take(10)
                    }
                }
                mSubwayStations.postValue(result)
            })
        }
    }

    private fun setUpDatabase() {
        if(mDatabase.getSubwayStationDao().getSize() == 0) {
            val subwayStations = mSubwayWebService.getSubwayStations()
            if (subwayStations.isNotEmpty()) {
                mDatabase.getSubwayStationDao().insert(subwayStations)
            }
        }

        if(mDatabase.getSubwayServiceDao().getSize() == 0) {
            val subwayServices = mSubwayWebService.getSubwayServices()
            if (subwayServices.isNotEmpty()) {
                mDatabase.getSubwayServiceDao().insert(subwayServices)
            }
        }

        if(mDatabase.getSubwayTimeDao().getSize() == 0) {
            val subwayTimes = mSubwayWebService.getSubwayTimes()
            if (subwayTimes.isNotEmpty()) {
                mDatabase.getSubwayTimeDao().insert(subwayTimes)
            }
        }

        if(mDatabase.getSubwayBoundDao().getSize() == 0) {
            val subwayBounds = mSubwayWebService.getSubwayBounds()
            if (subwayBounds.isNotEmpty()) {
                mDatabase.getSubwayBoundDao().insert(subwayBounds)
            }
        }
    }

    fun getSubwayStations(): LiveData<List<SubwayStation>>? {
        return mSubwayStations
    }

    fun getSubwayServices(subwayStation: SubwayStation): LiveData<List<SubwayService>>? {
        mExecutors.diskIO().execute {
            val lastUpdate = mDatabase.getSubwayServiceDao().getLastUpdated()
            if(lastUpdate > 0) {
                val timeSinceLastUpdate = Date().time - lastUpdate
                if (timeSinceLastUpdate >= 86400000) { //Update services every day.
                    val subwayServices = mSubwayWebService.getSubwayServices()
                    if (subwayServices.isNotEmpty()) {
                        mDatabase.getSubwayServiceDao().update(subwayServices)
                    }
                }
            }
        }
        return mDatabase.getSubwayServiceDao().get(subwayStation.service_ids)
    }

    fun getSubwayBounds(subwayService: SubwayService): LiveData<List<SubwayBound>>? {
        mExecutors.diskIO().execute {
            val lastUpdate = mDatabase.getSubwayBoundDao().getLastUpdated()
            if(lastUpdate > 0) {
                val timeSinceLastUpdate = Date().time - lastUpdate
                if (timeSinceLastUpdate >= 86400000) { //Update bounds every day.
                    val subwayBounds = mSubwayWebService.getSubwayBounds()
                    if (subwayBounds.isNotEmpty()) {
                        mDatabase.getSubwayBoundDao().update(subwayBounds)
                    }
                }
            }
        }
        return mDatabase.getSubwayBoundDao().get(subwayService.bound_ids)
    }

    fun getSubwayTimes(subwayStation: SubwayStation,
                       subwayService: SubwayService,
                       subwayBound: SubwayBound): LiveData<List<SubwayTime>>? {
        mExecutors.diskIO().execute {
            val lastUpdate = mDatabase.getSubwayTimeDao().getLastUpdated()
            if(lastUpdate > 0) {
                val timeSinceLastUpdate = Date().time - lastUpdate
                if (timeSinceLastUpdate >= 900000) { //Update times every 15 minutes.
                    val subwayTimes = mSubwayWebService.getSubwayTimes()
                    if (subwayTimes.isNotEmpty()) {
                        mDatabase.getSubwayTimeDao().deleteAll()
                        mDatabase.getSubwayTimeDao().insert(subwayTimes)
                    }
                }
            }
        }
        return mDatabase.getSubwayTimeDao().get(
                subwayStation.id,
                subwayService.id,
                subwayBound.direction,
                Date().time)
    }

    fun removeSubwayTime(subwayTime: SubwayTime) {
        mExecutors.diskIO().execute { mDatabase.getSubwayTimeDao().delete(subwayTime) }
    }

    private fun calculateDistance(
            latitude1: Double, longitude1: Double,
            latitude2: Double, longitude2: Double): Double {
        val theta = longitude1 - longitude2
        var distance = (sin(toRadians(latitude1)) * sin(toRadians(latitude2))) +
        (cos(toRadians(latitude1)) * cos(toRadians(latitude2)) *
        cos(toRadians(theta)))
        distance = toDegrees(acos(distance)) * 60 * 1.1515
        return distance //for kilometers, multiply this by 1.609344
    }
}