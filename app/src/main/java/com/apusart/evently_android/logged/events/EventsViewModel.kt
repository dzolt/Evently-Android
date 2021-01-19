package com.apusart.evently_android.logged.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.api.Event
import com.apusart.api.repositories.EventRepository

class EventsViewModel: ViewModel() {
    private val eventRepository = EventRepository()
    val events = eventRepository.getEvents()
}