package com.shahzorequreshi.famta.database.converters

import android.arch.persistence.room.TypeConverter
import com.shahzorequreshi.famta.database.entities.SubwayLineEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

/**
 * Converts subway event lists to jsons and vice versa.
 */
class SubwayLineEventListConverter {
    private val mMoshi: Moshi = Moshi.Builder().build()
    private val mJsonAdapter: JsonAdapter<List<SubwayLineEvent>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, SubwayLineEvent::class.java))
    @TypeConverter
    fun jsonToList(json: String): List<SubwayLineEvent> {
        return mJsonAdapter.fromJson(json) ?: listOf()
    }

    @TypeConverter
    fun listToJSON(list: List<SubwayLineEvent>): String {
        return mJsonAdapter.toJson(list)
    }
}