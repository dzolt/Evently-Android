package com.apusart.evently_android.logged.create_event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateEventViewModel: ViewModel() {
    val currentPage = MutableLiveData(1)

    fun add() {
        currentPage.value = currentPage.value?.plus(1)?.rem(6)
    }
}