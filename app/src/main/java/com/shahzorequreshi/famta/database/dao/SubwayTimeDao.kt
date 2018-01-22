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

    @Query("SELECT * FROM subway_time WHERE station_id == :stationId AND bound_id == :boundId")
    fun get(stationId: String, boundId: Long): LiveData<List<SubwayTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayTimes: SubwayTime)

    @Delete
    fun delete(subwayTime: SubwayTime)
}