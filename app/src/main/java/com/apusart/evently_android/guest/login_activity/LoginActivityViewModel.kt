package com.apusart.evently_android.guest.login_activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.api.Resource
import com.apusart.api.repositories.UserRepository
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

import java.lang.Exception

class LoginActivityViewModel(private val context: Context): ViewModel() {
    private val userRepository = UserRepository(context)
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val user = MutableLiveData<Resource<FirebaseUser>>()

    fun fbLogin(token: AccessToken) {
        viewModelScope.launch {
            try {
                user.value = Resource.pending()
                user.value = userRepository.facebookLogIn(token)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }

    fun logIn() {
        viewModelScope.launch {
            try {

                if (emailText.value == null || emailText.value.equals("")) {
                    return@launch
                }

                if (passwordText.value == null || passwordText.value.equals("")) {
                    return@launch
                }

                user.value = Resource.pending()
                user.value = userRepository.emailLogIn(emailText.value!!, passwordText.value!!)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }

    fun googleLogIn(idToken: String) {
        viewModelScope.launch {
            try {
                user.value = Resource.pending()
                user.value = userRepository.googleLogIn(idToken)
            } catch (e: Exception) {
                user.value = Resource.error(e.message)
            }
        }
    }
}