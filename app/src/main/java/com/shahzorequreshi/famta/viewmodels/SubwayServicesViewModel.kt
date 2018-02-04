package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * ViewModel that holds subway line information, such as subway services.
 */
class SubwayServicesViewModel(subwayStation: SubwayStation) : ViewModel() {
    private val mSubwayStation = subwayStation
    private var mSubwayServices: LiveData<List<SubwayService>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayServices == null) {
            mSubwayServices = mRepo.getSubwayServices(mSubwayStation)
        }
    }

    fun getSubwayServices(): LiveData<List<SubwayService>>? {
        return mSubwayServices
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway line into the ViewModel.
     */
    class Factory(subwayStation: SubwayStation): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayStation = subwayStation
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayServicesViewModel(mSubwayStation) as T
        }
    }
}