package com.shahzorequreshi.famta.database.converters

import android.arch.persistence.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

/**
 * Converts string lists to jsons and vice versa.
 */
class StringListConverter {
    private val mMoshi: Moshi = Moshi.Builder().build()
    private val mJsonAdapter: JsonAdapter<List<String>>
            = mMoshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))
    @TypeConverter
    fun jsonToList(json: String): List<String> {
        return mJsonAdapter.fromJson(json) ?: listOf()
    }

    @TypeConverter
    fun listToJSON(list: List<String>): String {
        return mJsonAdapter.toJson(list)
    }
}