package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * Defines methods for interacting with subway stations.
 */
@Dao
interface SubwayStationDao {
    @Query("SELECT * FROM subway_station")
    fun get(): LiveData<List<SubwayStation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayStations: List<SubwayStation>)

    @Delete
    fun delete(subwayStation: SubwayStation)
}