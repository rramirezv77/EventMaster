package com.rodrigo.eventmaster.di

import android.content.Context
import androidx.room.Room
import com.rodrigo.eventmaster.data.local.EventMasterDatabase
import com.rodrigo.eventmaster.data.local.dao.CategoryDao
import com.rodrigo.eventmaster.data.local.dao.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventMasterDatabase =
        Room.databaseBuilder(
            context,
            EventMasterDatabase::class.java,
            "event_master_database"
        ).build()

    @Provides
    fun provideCategoryDao(database: EventMasterDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideEventDao(database: EventMasterDatabase): EventDao = database.eventDao()
}
