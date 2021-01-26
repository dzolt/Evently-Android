package com.apusart.evently_android.logged.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class EventsViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    val events = eventRepository.getEvents()
    val event = MutableLiveData<Resource<Event>>()
    var eventDetails = Event()
    val joiningEvent = MutableLiveData<Resource<Unit>>()

    fun setEvent(id: String) {
        viewModelScope.launch {
            event.value = eventRepository.getEventById(id)
        }
    }

    fun joinEvent() {
        viewModelScope.launch {
            try {
                joiningEvent.value = Resource.pending()
                delay(2000)
                throw Exception()
            } catch (e: Exception) {
                joiningEvent.value = Resource.error(e.message)
            }
        }
    }
}