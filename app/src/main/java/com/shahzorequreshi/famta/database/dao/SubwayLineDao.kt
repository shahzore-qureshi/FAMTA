package com.shahzorequreshi.famta.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.shahzorequreshi.famta.database.objects.SubwayLine
import com.shahzorequreshi.famta.database.objects.SubwayStation

/**
 * Created by Shahzore Qureshi on 1/14/18.
 */
@Dao
interface SubwayLineDao {
    @Query("SELECT * FROM subway_line")
    fun all(): LiveData<List<SubwayLine>>

    @Query("SELECT * FROM subway_line WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): LiveData<List<SubwayLine>>

    @Query("SELECT * FROM subway_line WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): SubwayLine

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg subwayLines: SubwayLine)

    @Delete
    fun delete(subwayLine: SubwayLine)
}