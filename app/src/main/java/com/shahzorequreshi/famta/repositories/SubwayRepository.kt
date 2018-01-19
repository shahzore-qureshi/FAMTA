package com.shahzorequreshi.famta.repositories

import android.arch.lifecycle.LiveData
import android.content.Context
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.objects.SubwayLine
import com.shahzorequreshi.famta.threads.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Shahzore Qureshi on 1/17/18.
 */
@Singleton
class SubwayRepository {
    private var db: AppDatabase? = null
    private var executors: AppExecutors? = null

    companion object {
        @Volatile
        private var mInstance: SubwayRepository? = null

        fun getInstance(context: Context): SubwayRepository {
            mInstance = mInstance ?: synchronized(this) {
                mInstance ?: SubwayRepository()
            }
            mInstance?.db = AppDatabase.getInstance(context)
            mInstance?.executors = AppExecutors()
            return mInstance!!
        }
    }

    fun getSubwayLines(): LiveData<List<SubwayLine>>? {
        refreshData()
        return db?.getSubwayLineDao()?.all()
    }

    private fun refreshData() {
        executors?.diskIO()?.execute {
            val subwayLineBlue = SubwayLine("blue")
            val subwayLineOrange = SubwayLine("orange")
            db?.getSubwayLineDao()?.insertAll(subwayLineBlue, subwayLineOrange)
        }
    }
}