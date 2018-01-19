package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.shahzorequreshi.famta.database.objects.SubwayStation

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Dao
interface SubwayStationDao {
    @Query("SELECT * FROM subway_station")
    fun all(): LiveData<List<SubwayStation>>

    @Query("SELECT * FROM subway_station WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): LiveData<List<SubwayStation>>

    @Query("SELECT * FROM subway_station WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SubwayStation

    @Insert
    fun insertAll(vararg stations: SubwayStation)

    @Delete
    fun delete(station: SubwayStation)
}