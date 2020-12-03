package com.apusart.evently_android.guest.login_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Resource
import com.apusart.evently_android.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(): ViewModel() {

    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val user = MutableLiveData<Resource<FirebaseUser>>()

    fun logIn() {
        viewModelScope.launch {
            try {
                user.value = Resource.pending()
                Firebase.auth.signInWithEmailAndPassword(emailText.value!!, passwordText.value!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            user.value = Resource.success(Firebase.auth.currentUser!!)
                        } else {
                            user.value = Resource.error(it.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}