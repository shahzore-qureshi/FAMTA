package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayLine

/**
 * Defines methods for interacting with subway lines.
 */
@Dao
interface SubwayLineDao {
    @Query("SELECT * FROM subway_line")
    fun get(): LiveData<List<SubwayLine>>

    @Query("SELECT * FROM subway_line WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SubwayLine

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayLines: SubwayLine)

    @Delete
    fun delete(subwayLine: SubwayLine)
}