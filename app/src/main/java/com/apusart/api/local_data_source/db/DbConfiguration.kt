package com.apusart.api.local_data_source.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.apusart.api.Event
import com.apusart.api.local_data_source.db.dao.IEventDao

@Database(entities = [Event::class], version = 1)
abstract class EventlyDatabase: RoomDatabase() {
    abstract fun eventDao(): IEventDao

    companion object {
        private lateinit var applicationContext: Context

        val db by lazy {
            databaseBuilder(applicationContext, EventlyDatabase::class.java, "evently.db")
                .build()
        }

        fun initialize(appContext: Context) {
            applicationContext = appContext
        }
    }
}
