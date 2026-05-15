package com.rodrigo.eventmaster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigo.eventmaster.data.local.dao.CategoryDao
import com.rodrigo.eventmaster.data.local.dao.EventDao
import com.rodrigo.eventmaster.data.local.entity.CategoryEntity
import com.rodrigo.eventmaster.data.local.entity.EventEntity

@Database(
    entities = [CategoryEntity::class, EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventMasterDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun eventDao(): EventDao
}
