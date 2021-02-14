package com.apusart.api.remote_data_source.services

import com.apusart.api.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService {
    private val storage = FirebaseStorage.getInstance()
    private val db = Firebase.firestore

    private fun getUserStorageReference(id: String): StorageReference {
        return getReference("users/${id}")
    }

    fun getReference(path: String): StorageReference {
        return storage.reference.child(path)
    }


    suspend fun getUserById(id: String): User? {
        return db.collection("users")
            .document(id)
            .get()
            .await()
            .toObject(User::class.java)
    }

    suspend fun storeUser(user: User) {
        db.collection("users")
            .document(user.id)
            .set(user)
            .await()
    }
}