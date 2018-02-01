package com.shahzorequreshi.famta

import com.shahzorequreshi.famta.services.SubwayWebService
import org.junit.Test
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SubwayWebServiceTest {
    private lateinit var mService: SubwayWebService

    @Before
    fun getService() {
        mService = SubwayWebService()
    }

    @Test
    fun getSubwayStations() {
        val subwayStations = mService.getSubwayStations()
        for(subwayStation in subwayStations) {
            println(subwayStation.name)
        }
        assert(subwayStations.isNotEmpty())
    }

    @Test
    fun getSubwayServices() {
        val subwayServices = mService.getSubwayServices()
        for(subwayService in subwayServices) {
            println(subwayService.name)
        }
        assert(subwayServices.isNotEmpty())
    }

    @Test
    fun getSubwayTimes() {
        val subwayTimes = mService.getSubwayTimes()
        for(subwayTime in subwayTimes) {
            println(subwayTime.arrival_time)
        }
        assert(subwayTimes.isNotEmpty())
    }
}
