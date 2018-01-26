package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
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

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway station and bound into the ViewModel.
     */
    class Factory: ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayStationsViewModel() as T
        }
    }
}