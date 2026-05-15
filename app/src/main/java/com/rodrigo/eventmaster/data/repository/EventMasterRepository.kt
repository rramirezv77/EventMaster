package com.rodrigo.eventmaster.data.repository

import com.rodrigo.eventmaster.model.Category
import com.rodrigo.eventmaster.model.Event
import kotlinx.coroutines.flow.Flow

interface EventMasterRepository {
    fun observeCategories(): Flow<List<Category>>
    fun observeEvents(): Flow<List<Event>>
    fun observeEvent(eventId: Long): Flow<Event?>
    suspend fun seedDefaultCategories()
    suspend fun addCategory(name: String)
    suspend fun addEvent(title: String, description: String, categoryId: Long)
}
