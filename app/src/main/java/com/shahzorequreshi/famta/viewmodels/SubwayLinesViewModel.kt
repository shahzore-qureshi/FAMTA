package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import javax.inject.Inject
import com.shahzorequreshi.famta.MainApplication
import com.shahzorequreshi.famta.database.entities.SubwayLine

/**
 * ViewModel that holds subway line information.
 */
class SubwayLinesViewModel : ViewModel() {
    private var mSubwayLines: LiveData<List<SubwayLine>>? = null
    @Inject lateinit var mRepo: SubwayRepository

    init {
        MainApplication.component.inject(this)
        if(mSubwayLines == null) {
            mSubwayLines = mRepo.getSubwayLines()
        }
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        return mSubwayLines
    }
}