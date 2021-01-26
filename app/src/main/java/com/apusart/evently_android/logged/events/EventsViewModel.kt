package com.apusart.evently_android.logged.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository

class EventsViewModel: ViewModel() {
    private val eventRepository = EventRepository()
    val events = eventRepository.getEvents()

    lateinit var event: LiveData<Resource<Event>>
    var eventDetails = Event()
    fun setEvent(id: String) {
        event = eventRepository.getEventById(id)
    }

    fun joinEvent() {
        //TODO: Add user id (or hash) to database
    }
}