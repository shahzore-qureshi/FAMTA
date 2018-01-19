package com.shahzorequreshi.famta.threads

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by Shahzore Qureshi on 1/18/18.
 */
@Singleton
class AppExecutors {
    private val mDiskIO: Executor = Executors.newSingleThreadExecutor()
    private val mNetworkIO: Executor = Executors.newFixedThreadPool(3)
    private val mMainThread: Executor = MainThreadExecutor()

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}