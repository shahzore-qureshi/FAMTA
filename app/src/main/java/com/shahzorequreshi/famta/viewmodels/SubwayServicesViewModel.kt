package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * ViewModel that holds subway services.
 */
class SubwayServicesViewModel(val subwayServices: List<SubwayService>) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway line into the ViewModel.
     */
    class Factory(subwayServices: List<SubwayService>): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayServices = subwayServices
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayServicesViewModel(mSubwayServices) as T
        }
    }
}