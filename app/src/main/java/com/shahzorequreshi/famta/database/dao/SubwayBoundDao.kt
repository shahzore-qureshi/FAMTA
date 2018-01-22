package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayBound
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * Defines methods for interacting with subway bounds.
 */
@Dao
interface SubwayBoundDao {
    @Query("SELECT * FROM subway_bound")
    fun get(): LiveData<List<SubwayBound>>

    @Query("SELECT * FROM subway_bound WHERE service_id == :serviceId")
    fun get(serviceId: Long): LiveData<List<SubwayBound>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayBounds: SubwayBound)

    @Delete
    fun delete(subwayBound: SubwayBound)
}