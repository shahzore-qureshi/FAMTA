package com.shahzorequreshi.famta

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import org.junit.After
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.shahzorequreshi.famta.dagger.AppModule
import com.shahzorequreshi.famta.database.AppDatabase
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Simple database test.
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var mContext: Context
    private lateinit var mDb: AppDatabase

    @Before
    fun createDb() {
        mContext = InstrumentationRegistry.getTargetContext()
        mDb = AppModule(mContext).providesDatabase()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun createSubwayLine() {
        var test = mDb.getSubwayLineDao().all().blockingObserve()
        var size = test?.size
        if (size == null) {
            println("size is NULL")
        } else {
            println("size is: " + size)
        }

//        val subwayLineBlue = SubwayLine("blue")
//        val subwayLineOrange = SubwayLine("orange")
//        mSubwayLineDao.insertAll(subwayLineBlue, subwayLineOrange)
//
//        test = mSubwayLineDao.all().blockingObserve()
//        size = test?.size
//        if (size == null) {
//            println("size is NULL")
//        } else {
//            println("size is: " + size)
//        }

        /*
        val byName = mSubwayLineDao!!.findByName("blue")
        assertThat(byName, equalTo(subwayLineBlue))
        */
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(5, TimeUnit.SECONDS)
        return value
    }
}