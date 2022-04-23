package com.neugelb.viewmodel.activity

import com.neugelb.base.BaseViewModel
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository)