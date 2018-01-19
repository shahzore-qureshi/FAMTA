package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.shahzorequreshi.famta.repositories.SubwayRepository
import com.shahzorequreshi.famta.database.objects.SubwayLine
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider



/**
 * Created by Shahzore Qureshi on 1/17/18.
 */
class SubwayViewModel @Inject constructor(repo: SubwayRepository) : ViewModel() {
    private var mSubwayLines: LiveData<List<SubwayLine>>? = null

    init {
        if(mSubwayLines == null) {
            mSubwayLines = repo.getSubwayLines()
        }
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        return mSubwayLines
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    class Factory(private val mRepo: SubwayRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayViewModel(mRepo) as T
        }
    }
}