package com.rodrigo.eventmaster.data.repository

import com.rodrigo.eventmaster.data.local.dao.CategoryDao
import com.rodrigo.eventmaster.data.local.dao.EventDao
import com.rodrigo.eventmaster.data.local.entity.CategoryEntity
import com.rodrigo.eventmaster.data.local.entity.EventEntity
import com.rodrigo.eventmaster.data.mapper.toDomain
import com.rodrigo.eventmaster.model.Category
import com.rodrigo.eventmaster.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventMasterRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val eventDao: EventDao
) : EventMasterRepository {

    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories().map { categories ->
            categories.map { it.toDomain() }
        }

    override fun observeEvents(): Flow<List<Event>> =
        eventDao.observeEvents().map { events ->
            events.map { it.toDomain() }
        }

    override fun observeEvent(eventId: Long): Flow<Event?> =
        eventDao.observeEvent(eventId).map { it?.toDomain() }

    override suspend fun seedDefaultCategories() {
        if (categoryDao.countCategories() == 0) {
            listOf("M\u00fasica", "Tecnolog\u00eda", "Deportes").forEach { categoryName ->
                categoryDao.insertCategory(CategoryEntity(name = categoryName))
            }
        }
    }

    override suspend fun addCategory(name: String) {
        categoryDao.insertCategory(CategoryEntity(name = name.trim()))
    }

    override suspend fun addEvent(title: String, description: String, categoryId: Long) {
        eventDao.insertEvent(
            EventEntity(
                title = title.trim(),
                description = description.trim(),
                categoryId = categoryId
            )
        )
    }
}
