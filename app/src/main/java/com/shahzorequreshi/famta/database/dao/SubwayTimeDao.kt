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
            + "station_id == :stationId AND "
            + "service_id == :serviceId AND "
            + "bound_id == :boundId AND "
            + "arrival_time > :currentTime")
    fun get(stationId: String,
            serviceId: String,
            boundId: String,
            currentTime: Long): LiveData<List<SubwayTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayTimes: List<SubwayTime>)

    @Delete
    fun delete(subwayTime: SubwayTime)
}