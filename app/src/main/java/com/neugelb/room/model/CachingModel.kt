package com.neugelb.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "caching")
class CachingModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "modelId")
    val modelId: Int,
    @ColumnInfo(name = "modelValue")
    val modelValue: String,
    @ColumnInfo(name = "lastUpdatedDate")
    val lastUpdatedDate: Date,
    @ColumnInfo(name = "type")
    val type: Int,
)

