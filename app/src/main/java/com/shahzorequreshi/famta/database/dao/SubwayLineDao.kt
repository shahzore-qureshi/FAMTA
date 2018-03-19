package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.entities.SubwayLine
import com.shahzorequreshi.famta.database.entities.SubwayStation

/**
 * Defines methods for interacting with subway lines.
 */
@Dao
interface SubwayLineDao {
    @Query("SELECT * FROM subway_line ORDER BY name")
    fun get(): LiveData<List<SubwayLine>>

    @Query("SELECT COUNT(*) FROM subway_line")
    fun getSize(): Int

    @Query("SELECT last_updated FROM subway_line LIMIT 1")
    fun getLastUpdated(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayLines: List<SubwayLine>)

    @Update
    fun update(subwayLines: List<SubwayLine>)

    @Delete
    fun delete(subwayLine: SubwayLine)
}