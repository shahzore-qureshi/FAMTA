package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
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
    private var mSubwayStations = MediatorLiveData<List<SubwayStation>>()

    init {
        MainApplication.component.inject(this)
        setUpDatabase()
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
            val subwayTimes = mSubwayWebService.getSubwayTimes()
            if(subwayTimes.isNotEmpty()) {
                mDatabase.getSubwayTimeDao().insert(subwayTimes)
            }
            mDatabase.getSubwayBoundDao().insert(
                    SubwayBound("North", "N"),
                    SubwayBound("South", "S"))
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
        return mSubwayStations
    }

    fun getSubwayServices(subwayStation: SubwayStation): LiveData<List<SubwayService>>? {
        return mDatabase.getSubwayServiceDao().get(subwayStation.service_ids)
    }

    fun getSubwayBounds(): LiveData<List<SubwayBound>>? {
        return mDatabase.getSubwayBoundDao().get()
    }

    fun getSubwayTimes(subwayStation: SubwayStation,
                       subwayService: SubwayService,
                       subwayBound: SubwayBound): LiveData<List<SubwayTime>>? {
        return mDatabase.getSubwayTimeDao().get(
                subwayStation.stop_id,
                subwayService.name,
                subwayBound.direction,
                Date().time)
    }

    fun removeSubwayTime(subwayTime: SubwayTime) {
        mExecutors.diskIO().execute { mDatabase.getSubwayTimeDao().delete(subwayTime) }
    }
}