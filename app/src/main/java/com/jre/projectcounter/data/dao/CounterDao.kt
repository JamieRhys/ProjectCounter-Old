package com.jre.projectcounter.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jre.projectcounter.data.entities.Counter

@Dao
interface CounterDao {
    @Query("SELECT * FROM table_counters")
    fun getAll(): List<Counter>

    @Insert
    suspend fun insertCounter(counter: Counter)

    @Insert
    suspend fun insertCounters(counters: List<Counter>)

    @Delete
    suspend fun deleteCounter(counter: Counter)

    @Query("DELETE FROM table_counters")
    suspend fun deleteAll()
}