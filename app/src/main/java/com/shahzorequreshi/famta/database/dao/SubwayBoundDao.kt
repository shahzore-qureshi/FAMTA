package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayBound

/**
 * Defines methods for interacting with subway bounds.
 */
@Dao
interface SubwayBoundDao {
    @Query("SELECT * FROM subway_bound")
    fun get(): LiveData<List<SubwayBound>>

    @Query("SELECT * FROM subway_bound WHERE id IN (:bound_ids)")
    fun get(bound_ids: List<String>): LiveData<List<SubwayBound>>

    @Query("SELECT COUNT(*) FROM subway_bound")
    fun getSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayBounds: List<SubwayBound>)

    @Delete
    fun delete(subwayBound: SubwayBound)
}