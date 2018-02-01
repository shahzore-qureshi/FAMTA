package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.*
import com.shahzorequreshi.famta.services.SubwayWebService
import com.shahzorequreshi.famta.threads.AppExecutors
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
        setUpDatabase()
    }

    private fun setUpDatabase() {
        mExecutors.diskIO().execute {
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
                mDatabase.getSubwayBoundDao().insert(listOf(
                        SubwayBound("N", "North"),
                        SubwayBound("S", "South")))
            }
        }
    }

    fun setUserLocation(latitude: Double?, longitude: Double?) {
        if(latitude != null && longitude != null) {
            mSubwayStations.addSource(mDatabase.getSubwayStationDao().get(latitude, longitude), {
                mSubwayStations.postValue(it)
            })
        } else {
            mSubwayStations.addSource(mDatabase.getSubwayStationDao().get(), {
                mSubwayStations.postValue(it)
            })
        }
    }

    fun getSubwayStations(): LiveData<List<SubwayStation>>? {
        mExecutors.diskIO().execute {
            val lastUpdate =  mDatabase.getSubwayStationDao().getLastUpdated()
            if(lastUpdate > 0) {
                val timeSinceLastUpdate = Date().time - lastUpdate
                if (timeSinceLastUpdate >= 900000) { //Update stations every 15 minutes.
                    val subwayStations = mSubwayWebService.getSubwayStations()
                    if (subwayStations.isNotEmpty()) {
                        mDatabase.getSubwayStationDao().update(subwayStations)
                    }
                }
            }
        }
        return mSubwayStations
    }

    fun getSubwayServices(subwayStation: SubwayStation): LiveData<List<SubwayService>>? {
        mExecutors.diskIO().execute {
            val lastUpdate =  mDatabase.getSubwayServiceDao().getLastUpdated()
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

    fun getSubwayBounds(): LiveData<List<SubwayBound>>? {
        return mDatabase.getSubwayBoundDao().get()
    }

    fun getSubwayTimes(subwayStation: SubwayStation,
                       subwayService: SubwayService,
                       subwayBound: SubwayBound): LiveData<List<SubwayTime>>? {
        mExecutors.diskIO().execute {
            val lastUpdate =  mDatabase.getSubwayTimeDao().getLastUpdated()
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
                subwayBound.id,
                Date().time)
    }

    fun removeSubwayTime(subwayTime: SubwayTime) {
        mExecutors.diskIO().execute { mDatabase.getSubwayTimeDao().delete(subwayTime) }
    }
}