package com.rodrigo.eventmaster.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigo.eventmaster.data.repository.EventMasterRepository
import com.rodrigo.eventmaster.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: EventMasterRepository
) : ViewModel() {
    private val eventId = savedStateHandle.get<String>("eventId")?.toLongOrNull() ?: 0L

    val event: StateFlow<Event?> = repository.observeEvent(eventId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}
