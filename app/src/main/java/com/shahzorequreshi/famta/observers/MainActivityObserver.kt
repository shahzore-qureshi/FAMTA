package com.shahzorequreshi.famta.observers

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
class MainActivityObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initializeUI() {

    }
}