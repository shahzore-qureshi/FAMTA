package com.shahzorequreshi.famta.services

import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

/**
 * Standard web service provider for subway information.
 */
@Singleton
class SubwayWebService {
    private val mSubwayLineURL = "https://www.famta.ml/api/subway/lines"
    private val mSubwayServiceURL = "https://www.famta.ml/api/subway/services"
    private val mSubwayStationURL = "https://www.famta.ml/api/subway/stations"
    private val mSubwayTimeURL = "https://www.famta.ml/api/subway/times"
    private val mClient: OkHttpClient = OkHttpClient()
    private val mMoshi: Moshi = Moshi.Builder().build()
    private val mSubwayLineJsonAdapter: JsonAdapter<List<SubwayLine>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayLine::class.java))
    private val mSubwayServiceJsonAdapter: JsonAdapter<List<SubwayService>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayService::class.java))
    private val mSubwayStationJsonAdapter: JsonAdapter<List<SubwayStation>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayStation::class.java))
    private val mSubwayTimeJsonAdapter: JsonAdapter<List<SubwayTime>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayTime::class.java))

    fun getSubwayLines(): List<SubwayLine> {
        val request = Request.Builder().url(mSubwayLineURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            mSubwayLineJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
        } else {
            listOf()
        }
    }

    fun getSubwayServices(): List<SubwayService> {
        val request = Request.Builder().url(mSubwayServiceURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            mSubwayServiceJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
        } else {
            listOf()
        }
    }

    fun getSubwayStations(): List<SubwayStation> {
        val request = Request.Builder().url(mSubwayStationURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            mSubwayStationJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
        } else {
            listOf()
        }
    }

    fun getSubwayTimes(): List<SubwayTime> {
        val request = Request.Builder().url(mSubwayTimeURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            mSubwayTimeJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
        } else {
            listOf()
        }
    }
}