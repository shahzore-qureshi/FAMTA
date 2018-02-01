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

    @Query("SELECT * FROM subway_station ORDER BY "
            + "((:latitude - latitude) * (:latitude - latitude) + (:longitude - longitude) * (:longitude - longitude)) "
            + "LIMIT 0, 10")
    fun get(latitude: Double, longitude: Double): LiveData<List<SubwayStation>>

    @Query("SELECT COUNT(*) FROM subway_station")
    fun getSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayStations: List<SubwayStation>)

    @Delete
    fun delete(subwayStation: SubwayStation)
}