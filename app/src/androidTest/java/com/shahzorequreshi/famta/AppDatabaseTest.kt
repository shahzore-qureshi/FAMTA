package com.shahzorequreshi.famta

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.shahzorequreshi.famta.database.AppDatabase
import com.shahzorequreshi.famta.database.entities.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import org.junit.Assert.*
import java.util.*

/**
 * Simple database test.
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var mContext: Context
    private lateinit var mDatabase: AppDatabase

    @Before
    fun getDatabase() {
        mContext = InstrumentationRegistry.getTargetContext()
        mDatabase = Room.inMemoryDatabaseBuilder(mContext, AppDatabase::class.java).build()
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayStations() {
        //Check that there are no subway stations in the database yet.
        val noStations = mDatabase.getSubwayStationDao().get().blockingObserve()
        assertThat(noStations!!.size, equalTo(0))

        //Add stations to some bounds.
        mDatabase.getSubwayStationDao().insert(listOf(
                SubwayStation("101", "238 St", 0.0, 0.0, listOf("A")),
                SubwayStation("104", "231 St", 0.0, 0.0, listOf("B", "D")),
                SubwayStation("106", "225 St", 0.0, 0.0, listOf("A", "C"))))

        //Check if the stations were added correctly.
        val subwayStations = mDatabase.getSubwayStationDao().get().blockingObserve()
        assertThat(subwayStations!!.size, equalTo(3))

        //Delete all the stations.
        for (subwayStation in subwayStations) {
            mDatabase.getSubwayStationDao().delete(subwayStation)
        }

        //Check if the stations were deleted correctly.
        val deletedStations = mDatabase.getSubwayStationDao().get().blockingObserve()
        assertThat(deletedStations!!.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayServices() {
        //Check that there are no subway services in the database yet.
        val noServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(noServices!!.size, equalTo(0))

        //Add services to some lines.
        mDatabase.getSubwayServiceDao().insert(listOf(
                SubwayService("A", "A", 0),
                SubwayService("C", "C", 0),
                SubwayService("E", "E", 0)))
        mDatabase.getSubwayServiceDao().insert(listOf(
                SubwayService("3", "3", 0),
                SubwayService("4", "4", 0),
                SubwayService("5", "5", 0),
                SubwayService("6", "6", 0)))

        //Check if the services were added correctly.
        val subwayServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(subwayServices!!.size, equalTo(7))

        //Delete all the services.
        for(subwayService in subwayServices) {
            mDatabase.getSubwayServiceDao().delete(subwayService)
        }

        //Check if the services were deleted correctly.
        val deletedServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(deletedServices!!.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayBounds() {
        //Check that there are no subway bounds in the database yet.
        val noBounds = mDatabase.getSubwayBoundDao().get().blockingObserve()
        assertThat(noBounds!!.size, equalTo(0))

        //Add bounds to some services.
        mDatabase.getSubwayBoundDao().insert(listOf(
                SubwayBound("N", "North"),
                SubwayBound("S", "South")))

        //Check if the bounds were added correctly.
        val subwayBounds = mDatabase.getSubwayBoundDao().get().blockingObserve()
        assertThat(subwayBounds!!.size, equalTo(2))

        //Delete all the bounds.
        for (subwayBound in subwayBounds) {
            mDatabase.getSubwayBoundDao().delete(subwayBound)
        }

        //Check if the bounds were deleted correctly.
        val deletedBounds = mDatabase.getSubwayBoundDao().get().blockingObserve()
        assertThat(deletedBounds!!.size, equalTo(0))
    }

    @Test
    @Throws(Exception::class)
    fun testSubwayTimes() {
        //Check that there are no subway times in the database yet.
        val noTimes = mDatabase.getSubwayTimeDao().get().blockingObserve()
        assertThat(noTimes!!.size, equalTo(0))

        //Add times to some stations.
        mDatabase.getSubwayTimeDao().insert(listOf(
                SubwayTime("101", "A", "N", Date().time + 60000, 0),
                SubwayTime("102", "B", "S", Date().time + 120000, 0),
                SubwayTime("102", "B", "S", Date().time + 240000, 0),
                SubwayTime("103", "C", "N", Date().time + 360000, 0)
        ))

        //Check if the times were added correctly.
        val subwayTimes = mDatabase.getSubwayTimeDao().get().blockingObserve()
        assertThat(subwayTimes!!.size, equalTo(4))

        //Check if the times show up under a station, service, and bound.
        val timesForFirstStation = mDatabase.getSubwayTimeDao().get(
                "102", "B", "S", Date().time).blockingObserve()
        assertThat(timesForFirstStation!!.size, equalTo(2))

        //Delete all the times.
        for (subwayTime in subwayTimes) {
            mDatabase.getSubwayTimeDao().delete(subwayTime)
        }

        //Check if the times were deleted correctly.
        val deletedTimes = mDatabase.getSubwayTimeDao().get().blockingObserve()
        assertThat(deletedTimes!!.size, equalTo(0))
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