package com.shahzorequreshi.famta.services

import com.shahzorequreshi.famta.database.entities.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.BufferedSource
import java.util.*
import javax.inject.Singleton

/**
 * Standard web service provider for subway information.
 */
@Singleton
class SubwayWebService {
    private val mDomain = "https://www.famta.ml/api/subway"
    private val mSubwayStationURL = "$mDomain/stations"
    private val mSubwayServiceURL = "$mDomain/services"
    private val mSubwayBoundURL = "$mDomain/bounds"
    private val mSubwayTimeURL = "$mDomain/times"
    private val mSubwayLineURL = "$mDomain/lines"
    private val mClient: OkHttpClient = OkHttpClient()
    private val mMoshi: Moshi = Moshi.Builder().build()

    private val mSubwayStationJsonAdapter: JsonAdapter<List<SubwayStation>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayStation::class.java))
    private val mSubwayServiceJsonAdapter: JsonAdapter<List<SubwayService>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayService::class.java))
    private val mSubwayBoundJsonAdapter: JsonAdapter<List<SubwayBound>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayBound::class.java))
    private val mSubwayTimeJsonAdapter: JsonAdapter<List<SubwayTime>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayTime::class.java))
    private val mSubwayLineJsonAdapter: JsonAdapter<List<SubwayLine>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayLine::class.java))

    fun getSubwayStations(): List<SubwayStation> {
        val response = getSubwayData(mSubwayStationURL)
        return if(response != null) {
            val list = mSubwayStationJsonAdapter.fromJson(response) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayServices(): List<SubwayService> {
        val response = getSubwayData(mSubwayServiceURL)
        return if(response != null) {
            val list = mSubwayServiceJsonAdapter.fromJson(response) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayBounds(): List<SubwayBound> {
        val response = getSubwayData(mSubwayBoundURL)
        return if(response != null) {
            val list = mSubwayBoundJsonAdapter.fromJson(response) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayTimes(): List<SubwayTime> {
        val response = getSubwayData(mSubwayTimeURL)
        return if(response != null) {
            val list = mSubwayTimeJsonAdapter.fromJson(response) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayLines(): List<SubwayLine> {
        val response = getSubwayData(mSubwayLineURL)
        return if(response != null) {
            val list = mSubwayLineJsonAdapter.fromJson(response) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    private fun getSubwayData(url: String): BufferedSource? {
        val request = Request.Builder().url(url).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            response.body()!!.source()
        } else {
            null
        }
    }
}