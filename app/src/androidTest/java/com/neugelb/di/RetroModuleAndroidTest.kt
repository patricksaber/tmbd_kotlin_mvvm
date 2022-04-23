package com.neugelb.di

import com.neugelb.data.Api
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.database.DataDao
import io.mockk.mockk


class RetroModuleAndroidTest : RetroModule() {
    override fun getRepository(): Repository = mockk()
    override fun provideApi(): Api = mockk()
    override fun cashingRepository(dao: DataDao): CachingRepository = mockk()
    override fun getSession(): SessionManager = mockk()
}