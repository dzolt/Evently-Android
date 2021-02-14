package com.apusart.evently_android.logged.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository
import kotlinx.coroutines.launch

class CalendarViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    val events = MutableLiveData<Resource<List<Event>>>()

    init {
        updateEventList()
    }

    fun updateEventList() {
        viewModelScope.launch {
            events.value = eventRepository.getEventsForCurrentUser()
        }
    }
}