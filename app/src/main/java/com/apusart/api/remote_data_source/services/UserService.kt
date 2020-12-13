package com.apusart.api.remote_data_source.services

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

    fun getUser(id: String) {

    }
}