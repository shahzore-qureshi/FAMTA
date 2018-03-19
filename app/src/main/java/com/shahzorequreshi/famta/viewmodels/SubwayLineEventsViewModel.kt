package com.shahzorequreshi.famta.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shahzorequreshi.famta.database.entities.SubwayLineEvent

/**
 * ViewModel that holds subway line event information, such as delays or service work.
 */
class SubwayLineEventsViewModel(subwayLineEvents: List<SubwayLineEvent>) : ViewModel() {
    private val mSubwayLineEvents = subwayLineEvents

    fun getSubwayLineEvents(): List<SubwayLineEvent> {
        return mSubwayLineEvents
    }

    @Suppress("UNCHECKED_CAST")
    /**
     * A creator is used to inject the subway line events into the ViewModel.
     */
    class Factory(subwayLineEvents: List<SubwayLineEvent>): ViewModelProvider.NewInstanceFactory() {
        private val mSubwayLineEvents = subwayLineEvents
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SubwayLineEventsViewModel(mSubwayLineEvents) as T
        }
    }
}