package com.shahzorequreshi.famta

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import org.junit.After
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.dao.SubwayLineDao
import com.shahzorequreshi.famta.database.objects.SubwayLine
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by Shahzore Qureshi on 1/15/18.
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private var mSubwayLineDao: SubwayLineDao? = null
    private var mDb: AppDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = AppDatabase.getInstance(context)
        mSubwayLineDao = mDb?.getSubwayLineDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb?.close()
    }

    @Test
    @Throws(Exception::class)
    fun createSubwayLine() {
        var test = mSubwayLineDao?.all()?.blockingObserve()
        var size = test?.size
        if(size == null) {
            Log.e("AppDatabaseTest", "size is null")
        } else {
            Log.e("AppDatabaseTest", "Size is: " + size)
        }

        val subwayLineBlue = SubwayLine("blue")
        val subwayLineOrange = SubwayLine("orange")
        mSubwayLineDao!!.insertAll(subwayLineBlue, subwayLineOrange)

        test = mSubwayLineDao?.all()?.blockingObserve()
        size = test?.size
        if(size == null) {
            Log.e("AppDatabaseTest", "size is null")
        } else {
            Log.e("AppDatabaseTest", "Size is: " + size)
        }

        /*
        val byName = mSubwayLineDao!!.findByName("blue")
        assertThat(byName, equalTo(subwayLineBlue))
        */
    }

    fun <T> LiveData<T>.blockingObserve(): T? {
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