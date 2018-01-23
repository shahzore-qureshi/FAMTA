package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * ViewModel that holds subway bound information, such as subway stations.
 */
class SubwayBoundViewModel(subwayBound: SubwayBound) : ViewModel() {
    private val mSubwayBound = subwayBound
    private var mSubwayStations: LiveData<List<SubwayStation>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayStations == null) {
            mSubwayStations = mRepo.getSubwayStations(mSubwayBound)
        }
    }

    fun getSubwayStations(): LiveData<List<SubwayStation>>? {
        return mSubwayStations
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway bound into the ViewModel.
     */
    class Factory(subwayBound: SubwayBound): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayBound = subwayBound
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayBoundViewModel(mSubwayBound) as T
        }
    }
}