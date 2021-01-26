package com.apusart.api.remote_data_source.services

import android.net.Uri
import android.util.Log
import com.apusart.api.Event
import com.apusart.api.Resource
import com.apusart.api.User
import com.apusart.api.UserShort
import com.apusart.api.remote_data_source.BaseRemoteDataSource
import com.apusart.api.remote_data_source.IEventlyService
import com.apusart.tools.Defaults
import com.apusart.tools.Tools
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.lang.Exception
import javax.crypto.KeyGenerator
import javax.inject.Inject

class EventRemoteService : BaseRemoteDataSource() {
    private val storage = FirebaseStorage.getInstance()
    private val db = Firebase.firestore

    fun getReference(path: String): StorageReference {
        return storage.reference.child(path)
    }

    suspend fun generateIndex(): String {
        val free = db.collection("events")
        var index = Tools.getRandomString(15)

        while (free.document(index).get().await().exists())
            index = Tools.getRandomString(15)

        return index
    }

    suspend fun createEvent(event: Event) {
        db.collection("events")
            .document(event.id)
            .set(event)
            .await()
    }

    suspend fun addEventCoverPhoto(id: String, uri: Uri?): String? {
        if (uri == null)
            return null

        val path = "events/${id}/photos/cover.jpg"
        val file = getReference(path)

        file.putFile(uri).await()

        return file.downloadUrl.await().toString()
    }

    suspend fun getEvents(): Resource<List<Event>> {
        return Resource.success(
            db.collection("events")
                .get()
                .await()
                .toObjects(Event::class.java)
        )
    }

    suspend fun getEventById(id: String): Resource<Event> {
        return Resource.success(
            db.collection("events").whereEqualTo("id", id)
                .get()
                .await()
                .toObjects(Event::class.java).first()
        )
    }

    suspend fun getEventsForCurrentUser(): Resource<List<Event>> {
        val currentUser = Firebase.auth.currentUser
        val id = currentUser?.uid ?: "."
        val name = currentUser?.displayName ?: currentUser?.email ?: "No information"
        val user = UserShort(id, name)

        return Resource.success(
            db.collection("events")
                .whereArrayContains(
                    "joinedUsers",
                    user
                ).get()
                .await()
                .toObjects(Event::class.java)
        )
    }
}