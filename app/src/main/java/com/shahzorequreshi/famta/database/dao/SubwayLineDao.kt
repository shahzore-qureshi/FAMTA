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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayLine: SubwayLine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg subwayLines: SubwayLine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subwayLines: List<SubwayLine>)

    @Delete
    fun delete(subwayLine: SubwayLine)
}