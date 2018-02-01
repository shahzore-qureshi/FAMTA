package com.shahzorequreshi.famta.services

import com.shahzorequreshi.famta.database.entities.SubwayService
import com.shahzorequreshi.famta.database.entities.SubwayStation
import com.shahzorequreshi.famta.database.entities.SubwayTime
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import javax.inject.Singleton

/**
 * Standard web service provider for subway information.
 */
@Singleton
class SubwayWebService {
    private val mSubwayServiceURL = "https://www.famta.ml/api/subway/services"
    private val mSubwayStationURL = "https://www.famta.ml/api/subway/stations"
    private val mSubwayTimeURL = "https://www.famta.ml/api/subway/times"
    private val mClient: OkHttpClient = OkHttpClient()
    private val mMoshi: Moshi = Moshi.Builder().build()

    private val mSubwayServiceJsonAdapter: JsonAdapter<List<SubwayService>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayService::class.java))
    private val mSubwayStationJsonAdapter: JsonAdapter<List<SubwayStation>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayStation::class.java))
    private val mSubwayTimeJsonAdapter: JsonAdapter<List<SubwayTime>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayTime::class.java))

    fun getSubwayStations(): List<SubwayStation> {
        val request = Request.Builder().url(mSubwayStationURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            val list = mSubwayStationJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayServices(): List<SubwayService> {
        val request = Request.Builder().url(mSubwayServiceURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            val list = mSubwayServiceJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }

    fun getSubwayTimes(): List<SubwayTime> {
        val request = Request.Builder().url(mSubwayTimeURL).build()
        val response = mClient.newCall(request).execute()
        return if(response.isSuccessful && response.body() != null) {
            val list = mSubwayTimeJsonAdapter.fromJson(response.body()!!.source()) ?: listOf()
            list.forEach {
                it.last_updated = Date().time
            }
            list
        } else {
            listOf()
        }
    }
}