package com.apusart.api.remote_data_source.services

import com.apusart.api.remote_data_source.BaseRemoteDataSource
import com.apusart.api.remote_data_source.IEventlyService
import javax.inject.Inject

class EventRemoteService @Inject constructor(private val service: IEventlyService): BaseRemoteDataSource() {
}