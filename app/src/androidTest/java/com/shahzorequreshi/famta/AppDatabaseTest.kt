package com.shahzorequreshi.famta

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Before
import android.support.test.runner.AndroidJUnit4
import com.shahzorequreshi.famta.dagger.AppModule
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
    fun createDb() {
        mContext = InstrumentationRegistry.getTargetContext()
        mDatabase = AppModule(mContext).providesDatabase()
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
        //Check that there are no subway services in the database yet.
        val noServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(noServices!!.size, equalTo(0))

        //Add services to some lines.
        mDatabase.getSubwayServiceDao().insert(
                SubwayService("A", 0),
                SubwayService("C", 0),
                SubwayService("E", 0))
        mDatabase.getSubwayServiceDao().insert(
                SubwayService("B", 1),
                SubwayService("D", 1),
                SubwayService("F", 1),
                SubwayService("M", 1))

        //Check if the services were added correctly.
        val subwayServices = mDatabase.getSubwayServiceDao().get().blockingObserve()
        assertThat(subwayServices!!.size, equalTo(7))

        //Check if the A, C, and E services show up under the first line (id is 0).
        val servicesForFirstLine = mDatabase.getSubwayServiceDao().get(0).blockingObserve()
        assertThat(servicesForFirstLine!!.size, equalTo(3))

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
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("207 St, Manhattan", "North", 0),
                SubwayBound("Lefferts Blvd", "South", 0))
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("168 St, Manhattan", "North", 1),
                SubwayBound("Euclid Avenue", "South", 1))
        mDatabase.getSubwayBoundDao().insert(
                SubwayBound("Jamaica Center", "North", 2),
                SubwayBound("World Trade Center", "South", 2))

        //Check if the bounds were added correctly.
        val subwayBounds = mDatabase.getSubwayBoundDao().get().blockingObserve()
        assertThat(subwayBounds!!.size, equalTo(6))

        //Check if the bounds show up under a service.
        val boundsForServiceA = mDatabase.getSubwayBoundDao().get(0).blockingObserve()
        assertThat(boundsForServiceA!!.size, equalTo(2))

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
    fun testSubwayStations() {
        //Check that there are no subway stations in the database yet.
        val noStations = mDatabase.getSubwayStationDao().get().blockingObserve()
        assertThat(noStations!!.size, equalTo(0))

        //Add stations to some bounds.
        mDatabase.getSubwayStationDao().insert(
                SubwayStation("101N", "238 St", 0),
                SubwayStation("104N", "231 St", 0),
                SubwayStation("106N", "225 St", 0),
                SubwayStation("106S", "225 St", 1),
                SubwayStation("104S", "231 St", 1),
                SubwayStation("101S", "238 St", 1))

        //Check if the stations were added correctly.
        val subwayStations = mDatabase.getSubwayStationDao().get().blockingObserve()
        assertThat(subwayStations!!.size, equalTo(6))

        //Check if the stations show up under a bound.
        val stationsForFirstBound = mDatabase.getSubwayStationDao().get(0).blockingObserve()
        assertThat(stationsForFirstBound!!.size, equalTo(3))

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
    fun testSubwayTimes() {
        //Check that there are no subway times in the database yet.
        val noTimes = mDatabase.getSubwayTimeDao().get().blockingObserve()
        assertThat(noTimes!!.size, equalTo(0))

        //Add times to some stations.
        mDatabase.getSubwayTimeDao().insert(
                SubwayTime(Date(), "101N", 0),
                SubwayTime(Date(), "101N", 0),
                SubwayTime(Date(), "101N", 0),
                SubwayTime(Date(), "101S", 1),
                SubwayTime(Date(), "101S", 1),
                SubwayTime(Date(), "101S", 1))

        //Check if the times were added correctly.
        val subwayTimes = mDatabase.getSubwayTimeDao().get().blockingObserve()
        assertThat(subwayTimes!!.size, equalTo(6))

        //Check if the times show up under a station and bound.
        val timesForFirstStation = mDatabase.getSubwayTimeDao().get("101N", 0).blockingObserve()
        assertThat(timesForFirstStation!!.size, equalTo(3))

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