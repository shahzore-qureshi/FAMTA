package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * ViewModel that holds subway station information.
 */
class SubwayStationsViewModel : ViewModel() {
    private var mSubwayStations: LiveData<List<SubwayStation>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayStations == null) {
            mSubwayStations = mRepo.getSubwayStations()
        }
    }

    fun getSubwayStations(): LiveData<List<SubwayStation>>? {
        return mSubwayStations
    }
}