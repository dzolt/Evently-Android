package com.apusart.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        PENDING
    }

    companion object {
        fun<T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun<T> error(message: String?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun<T> pending(data: T? = null): Resource<T> {
            return Resource(Status.PENDING, data, null)
        }
    }
}

//To tylko test, z powodu tego ze jeśli zmienimy jakoś baze to trzeba potem zrobić migrację to jeśli nie bedzie działać to polecam
// wywalić appkę z VM
@Entity
data class Event(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String
)
