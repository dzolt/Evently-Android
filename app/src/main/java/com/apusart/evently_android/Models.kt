package com.apusart.evently_android

data class Category(
    val name: String,
    var isChecked: Boolean = false
)

data class EventPageDestination(
    val title: String,
    val subtitle: String,
    val destination: Int,
    val buttonText: String
)