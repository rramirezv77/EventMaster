package com.rodrigo.eventmaster.di

import com.rodrigo.eventmaster.data.repository.EventMasterRepository
import com.rodrigo.eventmaster.data.repository.EventMasterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEventMasterRepository(
        repositoryImpl: EventMasterRepositoryImpl
    ): EventMasterRepository
}
