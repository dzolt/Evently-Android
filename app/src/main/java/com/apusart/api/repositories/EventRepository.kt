package com.apusart.api.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.UserShort
import com.apusart.api.local_data_source.db.services.EventLocalService
import com.apusart.api.remote_data_source.services.EventRemoteService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class EventRepository {
    private val eventRemoteService = EventRemoteService()

    suspend fun createEvent(
        name: String,
        description: String,
        date: String,
        place: String,
        creator: UserShort,
        photoUri: Uri?,
        categories: List<String> = listOf()
    ): Resource<String> {

        val id = eventRemoteService.generateIndex()

        if (creator.id == ".")
            return Resource.error("Invalid user, you need to re-log")

        val photoPath = eventRemoteService.addEventCoverPhoto(id, photoUri) ?: ""
        val event = Event(
            id,
            name,
            description,
            date,
            place,
            creator,
            photoPath,
            categories,
            listOf(creator)
        )
        eventRemoteService.createEvent(event)

        return Resource.success(event.creator.id)
    }

    suspend fun getEvents(): Resource<List<Event>> {
        return eventRemoteService.getEvents()
    }

    suspend fun getEventById(id: String): Resource<Event> {
        return eventRemoteService.getEventById(id)

    }

    suspend fun getEventsForCurrentUser(): Resource<List<Event>> {
        return eventRemoteService.getEventsForCurrentUser()

    }

    suspend fun removeCurrentUserFromEvent(eventId: String): Resource<FirebaseUser> {
        return eventRemoteService.removeCurrentUserFromEvent(eventId)
    }

    suspend fun attend(event: Event): Resource<Unit> {
        return eventRemoteService.addCurrentUserToEvent(event.id)
    }

}