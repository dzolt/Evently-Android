package com.apusart.evently_android.logged.create_event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateEventViewModel: ViewModel() {
    val currentPage = MutableLiveData(0)
    val eventName = MutableLiveData<String>()
    val eventDescription = MutableLiveData<String>()

    //do wywalenia
    val test1 = MutableLiveData<String>()

    val test2 = MutableLiveData<String>()

    fun increment() {
        currentPage.value = currentPage.value?.plus(1)?.rem(6)
    }

    fun decrement() {
        currentPage.value = currentPage.value?.minus(1)?.rem(6)
    }
}