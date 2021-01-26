package com.apusart.evently_android.logged.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.repositories.EventRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class EventsViewModel : ViewModel() {
    private val eventRepository = EventRepository()
    val events = MutableLiveData<Resource<List<Event>>>()
    val event = MutableLiveData<Resource<Event>>()
    var eventDetails = Event()
    val joiningEvent = MutableLiveData<Resource<Unit>>()
    val alreadyJoined = MutableLiveData<Boolean>()

    init {
        refreshEvents()
    }

    fun setEvent(id: String) {
        viewModelScope.launch {
            val currentEvent = eventRepository.getEventById(id)
            event.value = currentEvent
            alreadyJoined.value = currentEvent.data?.joinedUsers?.map { it.id }?.contains(Firebase.auth.currentUser?.uid) ?: false
        }
    }

    fun joinEvent() {
        viewModelScope.launch {
            try {
                joiningEvent.value = Resource.pending()
                joiningEvent.value = eventRepository.attend(eventDetails)
            } catch (e: Exception) {
                joiningEvent.value = Resource.error(e.message)
            }
        }
    }

    fun refreshEvents() {
        viewModelScope.launch {
            events.value = eventRepository.getEvents()
        }
    }
}