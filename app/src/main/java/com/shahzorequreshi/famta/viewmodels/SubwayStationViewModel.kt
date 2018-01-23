package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayTime

/**
 * ViewModel that holds subway station information, such as arrival times.
 */
class SubwayStationViewModel(subwayStation: SubwayStation, subwayBound: SubwayBound) : ViewModel() {
    private val mSubwayStation = subwayStation
    private val mSubwayBound = subwayBound
    private var mSubwayTimes: LiveData<List<SubwayTime>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayTimes == null) {
            mSubwayTimes = mRepo.getSubwayTimes(mSubwayStation, mSubwayBound)
        }
    }

    fun getSubwayTimes(): LiveData<List<SubwayTime>>? {
        return mSubwayTimes
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway station and bound into the ViewModel.
     */
    class Factory(subwayStation: SubwayStation, subwayBound: SubwayBound): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayStation = subwayStation
        private val mSubwayBound = subwayBound
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayStationViewModel(mSubwayStation, mSubwayBound) as T
        }
    }
}