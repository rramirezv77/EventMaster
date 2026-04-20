package com.rodrigo.eventmaster.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rodrigo.eventmaster.model.Event

class EventViewModel : ViewModel() {

    var eventos = mutableStateListOf<Event>()

    fun agregarEvento(event: Event) {
        eventos.add(event)
    }
}
