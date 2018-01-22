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

    @Query("SELECT * FROM subway_station WHERE bound_id == :boundId")
    fun get(boundId: Long): LiveData<List<SubwayStation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayStations: SubwayStation)

    @Delete
    fun delete(subwayStation: SubwayStation)
}