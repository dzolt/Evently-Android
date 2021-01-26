package com.apusart.evently_android.logged.calendar

import androidx.lifecycle.ViewModel
import com.apusart.api.repositories.EventRepository

class CalendarViewModel: ViewModel() {
    private val eventRepository = EventRepository()
    val events = eventRepository.getEventsForCurrentUser()
}