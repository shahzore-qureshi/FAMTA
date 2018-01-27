package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * ViewModel that holds subway service information, such as subway bounds.
 */
class SubwayBoundsViewModel : ViewModel() {
    private var mSubwayBounds: LiveData<List<SubwayBound>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayBounds == null) {
            mSubwayBounds = mRepo.getSubwayBounds()
        }
    }

    fun getSubwayBounds(): LiveData<List<SubwayBound>>? {
        return mSubwayBounds
    }
}