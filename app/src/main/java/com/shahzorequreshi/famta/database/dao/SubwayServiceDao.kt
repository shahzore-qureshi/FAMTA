package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayService

/**
 * Defines methods for interacting with subway services.
 */
@Dao
interface SubwayServiceDao {
    @Query("SELECT * FROM subway_service")
    fun get(): LiveData<List<SubwayService>>

    @Query("SELECT * FROM subway_service WHERE id in (:service_ids)")
    fun get(service_ids: List<String>): LiveData<List<SubwayService>>

    @Query("SELECT COUNT(*) FROM subway_service")
    fun getSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayService: SubwayService)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayServices: SubwayService)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayServices: List<SubwayService>)

    @Delete
    fun delete(subwayService: SubwayService)
}