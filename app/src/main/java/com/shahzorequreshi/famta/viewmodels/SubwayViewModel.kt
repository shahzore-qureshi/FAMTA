package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.database.entities.SubwayLine
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.MainApplication

/**
 * ViewModel that holds subway information, such as subway lines.
 */
class SubwayViewModel: ViewModel() {
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