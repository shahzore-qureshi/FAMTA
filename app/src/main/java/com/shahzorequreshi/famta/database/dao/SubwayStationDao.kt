package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * Defines methods for interacting with subway stations.
 */
@Dao
interface SubwayStationDao {
    @Query("SELECT * FROM subway_station ORDER BY name")
    fun get(): LiveData<List<SubwayStation>>

    @Query("SELECT COUNT(*) FROM subway_station")
    fun getSize(): Int

    @Query("SELECT last_updated FROM subway_station LIMIT 1")
    fun getLastUpdated(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayStations: List<SubwayStation>)

    @Update
    fun update(subwayStations: List<SubwayStation>)

    @Delete
    fun delete(subwayStation: SubwayStation)
}