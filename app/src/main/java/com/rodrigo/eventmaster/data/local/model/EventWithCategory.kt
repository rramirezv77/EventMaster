package com.rodrigo.eventmaster.data.local.model

import androidx.room.ColumnInfo

data class EventWithCategory(
    val id: Long,
    val title: String,
    val description: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "category_name")
    val categoryName: String
)
