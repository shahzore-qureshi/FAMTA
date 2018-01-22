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
class SubwayLineViewModel(subwayLine: SubwayLine) : ViewModel() {
    private val mSubwayLine = subwayLine
    private var mSubwayServices: LiveData<List<SubwayService>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayServices == null) {
            mSubwayServices = mRepo.getSubwayServices(mSubwayLine)
        }
    }

    fun getSubwayServices(): LiveData<List<SubwayService>>? {
        return mSubwayServices
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    class Factory(subwayLine: SubwayLine): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayLine = subwayLine
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayLineViewModel(mSubwayLine) as T
        }
    }
}