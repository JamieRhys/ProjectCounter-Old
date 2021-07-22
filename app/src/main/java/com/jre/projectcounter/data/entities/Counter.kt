package com.jre.projectcounter.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_counters")
class Counter(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "counter_name") var counterName: String,
    @ColumnInfo(name = "project_id") val projectId: Long
) {
}