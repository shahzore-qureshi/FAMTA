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
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayService
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import org.junit.Assert.*

/**
 * Simple database test.
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var mContext: Context
    private lateinit var mDatabase: AppDatabase

    @Before
    fun createDb() {
        mContext = InstrumentationRegistry.getTargetContext()
        mDatabase = AppModule(mContext).providesDatabase()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayLines() {
        //Check that there are no subway lines in the database yet.
        val noLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(noLines!!.size, equalTo(0))

        //Add blue and orange subway lines.
        mDatabase.getSubwayLineDao().insert(SubwayLine("blue"), SubwayLine("orange"))

        //Check if the blue and orange lines were added correctly.
        val subwayLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(subwayLines!!.size, equalTo(2))

        //Delete all the lines.
        for(subwayLine in subwayLines) {
            mDatabase.getSubwayLineDao().delete(subwayLine)
        }

        //Check if the lines were deleted correctly.
        val deletedLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(deletedLines!!.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayServices() {
        //Check that there are no subway lines in the database yet.
        val noLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(noLines!!.size, equalTo(0))

        //Add blue and orange subway lines.
        mDatabase.getSubwayLineDao().insert(SubwayLine("blue"), SubwayLine("orange"))

        //Check if the blue and orange lines were added correctly.
        val subwayLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(subwayLines!!.size, equalTo(2))

        //Check that there are no subway services in the database yet.
        val noServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(noServices!!.size, equalTo(0))

        //Add services to the lines.
        for (subwayLine in subwayLines) {
            when(subwayLine.name) {
                "blue" -> {
                    mDatabase.getSubwayServiceDao().insert(
                            SubwayService("A", subwayLine.id),
                            SubwayService("C", subwayLine.id),
                            SubwayService("E", subwayLine.id))
                }
                "orange" -> {
                    mDatabase.getSubwayServiceDao().insert(
                            SubwayService("B", subwayLine.id),
                            SubwayService("D", subwayLine.id),
                            SubwayService("F", subwayLine.id),
                            SubwayService("M", subwayLine.id))
                }
            }
        }

        //Check if the services were added correctly.
        val subwayServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(subwayServices!!.size, equalTo(7))

        //Check if the A, C, and E services show up under the blue line.
        val servicesForBlueLine = mDatabase.getSubwayServiceDao()
                .get(subwayLines.find { subwayLine -> subwayLine.name == "blue" }!!.id)
                .blockingObserve()
        assertThat(servicesForBlueLine!!.size, equalTo(3))

        //Delete all the lines.
        for(subwayLine in subwayLines) {
            mDatabase.getSubwayLineDao().delete(subwayLine)
        }

        //Delete all the services.
        for(subwayService in subwayServices) {
            mDatabase.getSubwayServiceDao().delete(subwayService)
        }

        //Check if the lines and services were deleted correctly.
        val deletedLines = mDatabase.getSubwayLineDao().get().blockingObserve()
        assertThat(deletedLines!!.size, equalTo(0))

        val deletedServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(deletedServices!!.size, equalTo(0))
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