package com.apusart.api.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.UserShort
import com.apusart.api.local_data_source.db.services.EventLocalService
import com.apusart.api.remote_data_source.services.EventRemoteService
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

    fun getEvents(): LiveData<Resource<List<Event>>> {
        return liveData {
            emit(Resource.pending())
            emit(eventRemoteService.getEvents())
        }
    }

    fun getEventById(id: String): LiveData<Resource<Event>> {
        return liveData {
            emit(Resource.pending())
            emit(eventRemoteService.getEventById(id))
        }
    }


    fun attend(event: Event): Boolean {
        val currUser = Firebase.auth.currentUser
        val docRef =
            FirebaseFirestore.getInstance().collection("events")
                .document(event.id.toString()) ?: return false

//        val attendees: HashMap<Int, String> = HashMap<Int, String> ()
//        docRef.get().addOnSuccessListener { document ->
//            if (document != null){
//                for (i in document.data("attendees")){
//
//                }
//            }
//        }
//        attendees.put(currUser.hashCode(), decision)
        docRef.update("attendees", FieldValue.arrayUnion(currUser.hashCode()))
        return true
    }

}