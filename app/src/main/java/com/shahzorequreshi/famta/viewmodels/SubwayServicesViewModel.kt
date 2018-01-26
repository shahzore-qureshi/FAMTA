package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.database.entities.SubwayLine
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * ViewModel that holds subway line information, such as subway services.
 */
class SubwayServicesViewModel(subwayServices: List<String>) : ViewModel() {
    private val mSubwayServicesList = subwayServices
    private var mSubwayServices: LiveData<List<SubwayService>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayServices == null) {
            mSubwayServices = mRepo.getSubwayServices(mSubwayServicesList)
        }
    }

    fun getSubwayServices(): LiveData<List<SubwayService>>? {
        return mSubwayServices
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway line into the ViewModel.
     */
    class Factory(subwayServices: List<String>): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayServicesList = subwayServices
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayServicesViewModel(mSubwayServicesList) as T
        }
    }
}