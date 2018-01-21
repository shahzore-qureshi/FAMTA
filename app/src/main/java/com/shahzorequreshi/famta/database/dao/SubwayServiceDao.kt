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

    @Query("SELECT * FROM subway_service WHERE line_id == :lineId")
    fun get(lineId: Long): LiveData<List<SubwayService>>

    @Query("SELECT * FROM subway_service WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SubwayService

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayServices: SubwayService)

    @Delete
    fun delete(subwayService: SubwayService)
}