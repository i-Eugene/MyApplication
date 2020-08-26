package com.ieugene.kotlinlab.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDataBaseDao {

    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("Select * from daily_sleep_quality_table order by nightId desc limit 1")
    fun getTonight(): SleepNight?

    @Query("select * from daily_sleep_quality_table order by nightId desc")
    fun getAllNights(): LiveData<List<SleepNight>>
//    fun getAllNights(): List<SleepNight>
}