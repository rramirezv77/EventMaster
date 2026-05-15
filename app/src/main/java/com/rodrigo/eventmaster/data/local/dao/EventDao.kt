package com.rodrigo.eventmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigo.eventmaster.data.local.entity.EventEntity
import com.rodrigo.eventmaster.data.local.model.EventWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query(
        """
        SELECT events.id, events.title, events.description, events.category_id, categories.name AS category_name
        FROM events
        INNER JOIN categories ON categories.id = events.category_id
        ORDER BY categories.name ASC, events.title ASC
        """
    )
    fun observeEvents(): Flow<List<EventWithCategory>>

    @Query(
        """
        SELECT events.id, events.title, events.description, events.category_id, categories.name AS category_name
        FROM events
        INNER JOIN categories ON categories.id = events.category_id
        WHERE events.id = :eventId
        LIMIT 1
        """
    )
    fun observeEvent(eventId: Long): Flow<EventWithCategory?>

    @Insert
    suspend fun insertEvent(event: EventEntity): Long
}
