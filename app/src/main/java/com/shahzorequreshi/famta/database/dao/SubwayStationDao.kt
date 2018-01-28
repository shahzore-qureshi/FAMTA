package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * Defines methods for interacting with subway stations.
 */
@Dao
interface SubwayStationDao {
    @Query("SELECT * FROM subway_station ORDER BY stop_name")
    fun get(): LiveData<List<SubwayStation>>

    @Query("SELECT * FROM subway_station ORDER BY "
            + "((:latitude - stop_lat) * (:latitude - stop_lat) + (:longitude - stop_lon) * (:longitude - stop_lon)) "
            + "LIMIT 0, 10")
    fun get(latitude: Double, longitude: Double): LiveData<List<SubwayStation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayStations: List<SubwayStation>)

    @Delete
    fun delete(subwayStation: SubwayStation)
}