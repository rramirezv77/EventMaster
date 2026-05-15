package com.rodrigo.eventmaster.data.mapper

import com.rodrigo.eventmaster.data.local.entity.CategoryEntity
import com.rodrigo.eventmaster.data.local.entity.EventEntity
import com.rodrigo.eventmaster.data.local.model.EventWithCategory
import com.rodrigo.eventmaster.model.Category
import com.rodrigo.eventmaster.model.Event

fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name
)

fun EventWithCategory.toDomain() = Event(
    id = id,
    title = title,
    description = description,
    categoryId = categoryId,
    categoryName = categoryName
)

fun Event.toEntity() = EventEntity(
    id = id,
    title = title,
    description = description,
    categoryId = categoryId
)
