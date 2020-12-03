package com.apusart.evently_android.guest.initial_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import javax.inject.Inject

class InitialActivityViewModel @Inject constructor(): ViewModel() {
    val currentUser = MutableLiveData(Firebase.auth.currentUser)
}