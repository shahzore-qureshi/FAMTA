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
    fun getSubwayLines() {
        val subwayLines = mService.getSubwayLines()
        for(subwayLine in subwayLines) {
            println(subwayLine.name)
        }
    }

    @Test
    fun getSubwayServices() {
        val subwayServices = mService.getSubwayServices()
        for(subwayService in subwayServices) {
            println(subwayService.name)
        }
    }

    @Test
    fun getSubwayStations() {
        val subwayStations = mService.getSubwayStations()
        for(subwayStation in subwayStations) {
            println(subwayStation.stop_name)
        }
    }
}
