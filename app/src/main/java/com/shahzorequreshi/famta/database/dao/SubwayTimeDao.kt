package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayTime

/**
 * Defines methods for interacting with subway times.
 */
@Dao
interface SubwayTimeDao {
    @Query("SELECT * FROM subway_time")
    fun get(): LiveData<List<SubwayTime>>

    @Query("SELECT * FROM subway_time WHERE "
            + "station_id == :station_id AND "
            + "service_id == :service_id AND "
            + "bound_direction == :bound_direction AND "
            + "arrival_time > :current_time")
    fun get(station_id: String,
            service_id: String,
            bound_direction: String,
            current_time: Long): LiveData<List<SubwayTime>>

    @Query("SELECT COUNT(*) FROM subway_time")
    fun getSize(): Int

    @Query("SELECT last_updated FROM subway_time LIMIT 1")
    fun getLastUpdated(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayTimes: List<SubwayTime>)

    @Delete
    fun delete(subwayTime: SubwayTime)

    @Query("DELETE FROM subway_time")
    fun deleteAll()
}