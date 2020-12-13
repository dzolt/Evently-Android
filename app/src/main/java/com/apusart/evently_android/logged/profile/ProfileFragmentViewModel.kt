package com.apusart.evently_android.logged.profile

import android.content.Context
import android.net.Uri

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Resource
import com.apusart.api.repositories.UserRepository
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class ProfileFragmentViewModel constructor(context: Context): ViewModel() {
    private val userRepository = UserRepository(context)
    val currentUser = MutableLiveData<Resource<FirebaseUser?>>()
    val profilePicture = MutableLiveData(Resource.success(Uri.parse(Firebase.auth.currentUser?.photoUrl.toString() + "?height=500")))
    val isSignedOut = MutableLiveData<Resource<Boolean>>()

    fun signOut() {
        viewModelScope.launch {
            try {
                currentUser.value = Resource.pending()
                isSignedOut.value = userRepository.signOut()
            } catch (e: Exception) {
                currentUser.value = Resource.error(e.message)
            }
        }
    }

    fun uploadImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            try {
                val id = Firebase.auth.currentUser?.uid
                val pictureName = "profilePicture"
                if(id != null) {
                    userRepository.uploadProfilePhoto(uri, id, pictureName)
                    profilePicture.value = userRepository.getDownloadLink("users/${id}/${pictureName}.jpg")
                }
            } catch (e: Exception) {
                profilePicture.value = Resource.error(e.message)
            }
        }
    }
}