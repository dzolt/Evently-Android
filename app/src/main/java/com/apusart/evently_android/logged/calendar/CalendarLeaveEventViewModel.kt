package com.apusart.evently_android.logged.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository

class CalendarLeaveEventViewModel: ViewModel() {
    private val eventRepository = EventRepository()
    lateinit var event: LiveData<Resource<Event>>
    var eventDetails = Event()
    fun setEvent(id: String) {
        event = eventRepository.getEventById(id)
    }
}
