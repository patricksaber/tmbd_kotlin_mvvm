package com.neugelb.viewmodel.activity

import com.neugelb.base.BaseViewModel
import com.neugelb.config.PREF_REMEMBER
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository) {

    val checkUserFlow: Flow<Boolean> = flow { emit(session.getPreBool(key = PREF_REMEMBER)) }

}