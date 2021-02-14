package com.apusart.evently_android.logged.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class CalendarLeaveEventViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    val event = MutableLiveData<Resource<Event>>()
    var eventDetails = Event()
    val leavingEvent = MutableLiveData<Resource<Unit>>()

    fun setEvent(id: String) {
        viewModelScope.launch {
            event.value = eventRepository.getEventById(id)
        }
    }

    fun leaveEvent() {
        viewModelScope.launch {
            try {
                leavingEvent.value = Resource.pending()
                eventRepository.removeCurrentUserFromEvent(eventDetails.id)
                leavingEvent.value = Resource.success(Unit)
            } catch (e: Exception) {
                leavingEvent.value = Resource.error(e.message)
            }
        }
    }
}
