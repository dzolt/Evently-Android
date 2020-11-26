package com.apusart.api.repositories

import com.apusart.api.local_data_source.db.services.EventLocalService
import com.apusart.api.remote_data_source.services.EventRemoteService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventLocalService: EventLocalService,
    private val eventRemoteDataService: EventRemoteService
) {
}