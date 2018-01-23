package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.database.entities.SubwayLine
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * ViewModel that holds subway service information, such as subway bounds.
 */
class SubwayServiceViewModel(subwayService: SubwayService) : ViewModel() {
    private val mSubwayService = subwayService
    private var mSubwayBounds: LiveData<List<SubwayBound>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayBounds == null) {
            mSubwayBounds = mRepo.getSubwayBounds(mSubwayService)
        }
    }

    fun getSubwayBounds(): LiveData<List<SubwayBound>>? {
        return mSubwayBounds
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway service into the ViewModel.
     */
    class Factory(subwayService: SubwayService): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayService = subwayService
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayServiceViewModel(mSubwayService) as T
        }
    }
}