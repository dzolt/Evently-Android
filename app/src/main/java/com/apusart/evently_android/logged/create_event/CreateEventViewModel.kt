package com.apusart.evently_android.logged.create_event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.evently_android.Category

class CreateEventViewModel: ViewModel() {
    val currentPage = MutableLiveData(0)
    val eventName = MutableLiveData<String>()
    val eventDescription = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val place = MutableLiveData<String>()
    val categories = listOf(
        Category("domówka"),
        Category("klub"),
        Category("kolacja"),
        Category("grill"),
        Category("urodziny"),
        Category("integracja"),
        Category("plener")
    )

    fun increment() {
        currentPage.value = currentPage.value?.plus(1)?.rem(6)
    }

    fun decrement() {
        currentPage.value = currentPage.value?.minus(1)?.rem(6)
    }
}